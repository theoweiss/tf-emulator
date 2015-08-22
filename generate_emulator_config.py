#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Java Emulator Generator
Copyright (C) 2015 Theo Weiß <theo@m1theo.org>

generate_emulator_bindings.py: Generator for Emulator bindings

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

import argparse
import sys
import os

sys.path.append(os.path.split(os.getcwd())[0])
import common
import emulator_common

enabled_devices = []
skip_disabled = False
port = 1234

class EmulatorConfigGenerator(common.BindingsGenerator):
    emulatorconfig = ''
    uidgenerator = emulator_common.UidGenerator()

    def emulator_config_start(self, port):
        start = """
{{
  "port": {0},
  "devices": ["""

        return start.format(port)

    def emulator_config_end(self):
        end = """
  ]
}
"""
        return end

    def emulator_config_device(self):
        device = """
   {{
     "type": "{0}",
     "uid": "{1}",
     "enabled": {2}
   }},"""
  
        return device
    
    def prepare(self):
        self.emulatorconfig += self.emulator_config_start(port)

    def get_bindings_name(self):
        return 'emulator'

    def get_device_class(self):
        return emulator_common.JavaDevice

    def generate(self, device):
        uid = self.uidgenerator.get_uid_from_name(device.get_headless_camel_case_name())
        device_name = device.get_java_class_name()
        enabled = 'false'
        if device_name in enabled_devices:
            enabled = 'true'
            self.emulatorconfig += self.emulator_config_device().format(device_name, uid, enabled)
        else:
            if not skip_disabled:
                self.emulatorconfig += self.emulator_config_device().format(device_name, uid, enabled)

    def finish(self):
        emulatorconfigname = 'emulatorconfig.json'
        resourcessubdir = os.path.join('tfemulator', 'src', 'main', 'resources')
        emulatorconfigfh = open(os.path.join(self.get_bindings_root_directory(), resourcessubdir, emulatorconfigname), 'wb')
        config = self.emulatorconfig[:-1] # remove the last comma from devices list to get valid json
        config += self.emulator_config_end()
        emulatorconfigfh.write(config)
        emulatorconfigfh.close()

        common.BindingsGenerator.finish(self)

def generate(bindings_root_directory):
    common.generate(bindings_root_directory, 'en', EmulatorConfigGenerator)

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('devices', nargs='*',
                   help='devices which should be enabled')
    parser.add_argument('--skip-disabled', dest='skip', default=False, action='store_true',
                        help='don\'t add disabled devices to the configuration')
    parser.add_argument('--port', type=int, default=1234, help='brickd port')
    args = parser.parse_args()
    enabled_devices = args.devices
    skip_disabled = args.skip
    port = args.port
    generate(os.getcwd())
