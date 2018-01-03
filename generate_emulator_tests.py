#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Java Emulator Tests Generator
Copyright (C) 2015 Theo Weiß <theo@m1theo.org>

generate_emulator_tests.py: Generator for Emulator bindings

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public
License along with this program; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA 02111-1307, USA.
"""

import sys
import os

sys.path.append(os.path.split(os.getcwd())[0])
import common
import emulator_common
import emulator_license

class JavaBindingsDevice(emulator_common.JavaDevice):
    def get_generated_hint_docstring(self):
        hint = """
/* ***********************************************************
 * This file was automatically generated.      *
 *                                                           *
 * If you have a bugfix for this file and want to commit it, *
 * please fix the bug in the emulator generator.             *
 *************************************************************/
"""
        return hint

    def get_start_method_end(self):
        cbs_end = '  }\n'
        return cbs_end
    
    def get_java_class_end(self):
        cbs_end = '}\n'
        return cbs_end
        
    def get_java_function_id_definitions(self):
        function_ids = ''
        function_id = '  public final static byte {2}_{0} = (byte){1};\n'
        for packet in self.get_packets():
            function_ids += function_id.format(packet.get_upper_case_name(),
                                               packet.get_function_id(),
                                               packet.get_type().upper())
        return function_ids

    def get_test_import(self):
        imports = """package org.m1theo.tfemulator.tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.m1theo.tfemulator.Utils;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.{0};
import com.tinkerforge.IPConnection;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
"""
    
        return imports.format(self.get_java_class_name())

    def get_test_class(self, uid):
        classbody = """
@RunWith(VertxUnitRunner.class)
public class {0}Test {{

  Vertx vertx;
  IPConnection ipcon;
  {0} device;

  @Before
  public void before(TestContext context) {{
    String uid = "{1}";
    String host = "localhost";
    int port = 1234;
    JsonObject emuconfig = new JsonObject().put("devices", new JsonArray().add(
        new JsonObject().put("type", "{0}").put("uid", uid).put("enabled", true)));
    System.out.println(emuconfig.encodePrettily());
    DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(emuconfig);

    vertx = Vertx.vertx();
    Async async = context.async();
    vertx.deployVerticle("org.m1theo.tfemulator.Brickd", deploymentOptions, res -> {{
      async.complete();
    }});
    ipcon = new IPConnection(); // Create IP connection
    try {{
      try {{
        Thread.sleep(100);
      }} catch (InterruptedException e) {{
        // TODO Auto-generated catch block
        e.printStackTrace();
      }}
      ipcon.connect(host, port);
    }} catch (AlreadyConnectedException | IOException e) {{
      context.fail(e);
    }}
    device = new {0}(uid, ipcon);
  }}

  @After
  public void after(TestContext context) {{
    // ipcon.disconnect();
    vertx.close(context.asyncAssertSuccess());
  }}
"""
        return classbody.format(self.get_java_class_name(), uid)


    def get_test_booleans(self):
        methods = ''
        method = """
  @Test
  public void test{0}(TestContext context) {{
    try {{
      device.setResponseExpectedAll(true);
      device.{1}(); //enable
      //assert enabled
      {4} value = device.{3}();
{5}
      device.{2}(); //disable
      // assert disabled
      value = device.{3}();
{6}
    }} catch (Exception e) {{
      context.fail(e);
    }}
  }}
"""        
        assert_enabled='context.assertTrue(value);\n'
        assert_disabled='context.assertFalse(value);\n'
        
        booleans = self.get_booleans()
        for key in booleans.keys():
            packet = booleans[key][0]
            name_enable = booleans[key][1]
            name_disable = booleans[key][2]
            name_isenabled = booleans[key][3]
            type = packet.get_java_return_type()
            if type == "short":
                assert_enabled='      context.assertEquals(value, 1);\n'
                assert_disabled='      context.assertEquals(value, 0);\n'
            methods += method.format(key, name_enable, name_disable, name_isenabled, type, 
                                     assert_enabled, assert_disabled)
            
        return methods

    def get_test_sensors(self):
        methods = ''
        method = """
  @Test
  public void test{4}{0}(TestContext context) {{
    try {{
      {1} value = device.{0}({3});
      {2}
    }} catch (Exception e) {{
      context.fail(e);
    }}
  }}
"""        
        sensors = self.get_sensors()
        for key in sensors.keys():
            packet = sensors[key][0]
            method_args = []
            #0: methodname,  1: type of return value, 2: asserts
            return_type, test_asserts, args = packet.get_test_return_values()
            if return_type == None:
                return_type = self.get_java_class_name() + '.' + packet.get_java_object_name()
            if not args:
                methods += method.format(key, return_type, test_asserts, '', '')
            else:
                for arg in args:
                    if len(args) == 1:
                        if arg == 'boolean':
                            methods += method.format(key, return_type, test_asserts, 'true', '1')
                            methods += method.format(key, return_type, test_asserts, 'false', '2')
                        elif arg == 'short':
                            methods += method.format(key, return_type, test_asserts, '(short) 1', '')
                        else:
                            #TODO:
                            print "omitting method: don't know how to handle arg type " + arg
                    else:
                        #TODO concatenamte args
                        print "omitting method: don't know how to handle multiargs"

        return methods

    def get_test_source(self, uid):
        source  = emulator_license.get_license()
        source += self.get_generated_hint_docstring()
        source += self.get_test_import()
        source += self.get_test_class(uid)
        source += self.get_test_sensors()
        source += self.get_test_booleans()
        self.get_method_categories()
        #source += self.get_java_return_objects()
        source += self.get_java_class_end()
        
        return source


class JavaBindingsPacket(emulator_common.JavaPacket):

    def get_test_return_values(self):
        test_values = ""
        test_assert = """context.assertEquals({0}, value{1});
      """
        test_assert_arr = """      for (int i = 0; i < value{1}.length; i++) {{
        context.assertEquals({0}, value{1}[i]);
      }}
"""
        return_type = None
        args = []
        if len(self.get_elements('out')) == 1:
            value_field = ''
            for element in self.get_elements('out'):
                return_type = element.get_java_type()
                if element.get_cardinality() > 1:
                    return_type += '[]'
                    test_values += test_assert_arr.format(element.get_random_value_function(), value_field)
                else:
                    test_values += test_assert.format(element.get_random_value_function(), value_field)
        elif len(self.get_elements('out')) > 1:
            value_field = '.{0}'
            for element in self.get_elements('out'):
                if element.get_cardinality() > 1 and element.get_type() != 'string':
                    test_values += test_assert_arr.format(element.get_random_value_function(),
                                                          value_field.format(element.get_headless_camel_case_name()))
                else:
                    test_values += test_assert.format(element.get_random_value_function(), 
                                                  value_field.format(element.get_headless_camel_case_name()))
        if len(self.get_elements('in')) != 0:
            for element in self.get_elements('in'):
                args.append(element.get_java_type())

        return return_type, test_values, args


class JavaBindingsGenerator(common.BindingsGenerator):
    released_files_name_prefix = 'generatortests'
    testsubdir = os.path.join('tfemulator', 'src', 'test', 'java', 'org', 'm1theo', 'tfemulator', 'tests')
    uidgenerator = emulator_common.UidGenerator()
    readme = ''
    model_config_dir = 'model'

    def prepare(self):
        common.recreate_directory(os.path.join(self.get_bindings_root_directory(), self.testsubdir))

    def get_bindings_name(self):
        return 'emulator'

    def get_device_class(self):
        return JavaBindingsDevice

    def get_packet_class(self):
        return JavaBindingsPacket

    def get_element_class(self):
        return emulator_common.JavaElement

    def generate(self, device):
        ## generate tests
        uid = self.uidgenerator.get_uid_from_name(device.get_headless_camel_case_name())
        testfilename = '{0}Test.java'.format(device.get_java_class_name())
        javatest = open(os.path.join(self.get_bindings_root_directory(), self.testsubdir, testfilename), 'wb')
        javatest.write(device.get_test_source(uid))
        javatest.close()
        

def generate(bindings_root_directory):
    common.generate(bindings_root_directory, 'en', JavaBindingsGenerator)

if __name__ == "__main__":
    generate(os.getcwd())
