/*
 *  Copyright (c) 2015 Thomas Weiss <theo@m1theo.org>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
/* ***********************************************************
 * This file was automatically generated.      *
 *                                                           *
 * If you have a bugfix for this file and want to commit it, *
 * please fix the bug in the emulator generator.             *
 *************************************************************/

package org.m1theo.tfemulator.devices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import org.m1theo.tfemulator.Brickd;
import org.m1theo.tfemulator.CommonServices;
import org.m1theo.tfemulator.Utils;
import org.m1theo.tfemulator.protocol.Packet;

/**
 * Basis to build stacks and has 4 Bricklet ports
 */
public class BrickMaster extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 13;
public final static String DEVICE_DISPLAY_NAME = "Master Brick";

  public final static byte FUNCTION_GET_STACK_VOLTAGE = (byte)1;
  public final static byte FUNCTION_GET_STACK_CURRENT = (byte)2;
  public final static byte FUNCTION_SET_EXTENSION_TYPE = (byte)3;
  public final static byte FUNCTION_GET_EXTENSION_TYPE = (byte)4;
  public final static byte FUNCTION_IS_CHIBI_PRESENT = (byte)5;
  public final static byte FUNCTION_SET_CHIBI_ADDRESS = (byte)6;
  public final static byte FUNCTION_GET_CHIBI_ADDRESS = (byte)7;
  public final static byte FUNCTION_SET_CHIBI_MASTER_ADDRESS = (byte)8;
  public final static byte FUNCTION_GET_CHIBI_MASTER_ADDRESS = (byte)9;
  public final static byte FUNCTION_SET_CHIBI_SLAVE_ADDRESS = (byte)10;
  public final static byte FUNCTION_GET_CHIBI_SLAVE_ADDRESS = (byte)11;
  public final static byte FUNCTION_GET_CHIBI_SIGNAL_STRENGTH = (byte)12;
  public final static byte FUNCTION_GET_CHIBI_ERROR_LOG = (byte)13;
  public final static byte FUNCTION_SET_CHIBI_FREQUENCY = (byte)14;
  public final static byte FUNCTION_GET_CHIBI_FREQUENCY = (byte)15;
  public final static byte FUNCTION_SET_CHIBI_CHANNEL = (byte)16;
  public final static byte FUNCTION_GET_CHIBI_CHANNEL = (byte)17;
  public final static byte FUNCTION_IS_RS485_PRESENT = (byte)18;
  public final static byte FUNCTION_SET_RS485_ADDRESS = (byte)19;
  public final static byte FUNCTION_GET_RS485_ADDRESS = (byte)20;
  public final static byte FUNCTION_SET_RS485_SLAVE_ADDRESS = (byte)21;
  public final static byte FUNCTION_GET_RS485_SLAVE_ADDRESS = (byte)22;
  public final static byte FUNCTION_GET_RS485_ERROR_LOG = (byte)23;
  public final static byte FUNCTION_SET_RS485_CONFIGURATION = (byte)24;
  public final static byte FUNCTION_GET_RS485_CONFIGURATION = (byte)25;
  public final static byte FUNCTION_IS_WIFI_PRESENT = (byte)26;
  public final static byte FUNCTION_SET_WIFI_CONFIGURATION = (byte)27;
  public final static byte FUNCTION_GET_WIFI_CONFIGURATION = (byte)28;
  public final static byte FUNCTION_SET_WIFI_ENCRYPTION = (byte)29;
  public final static byte FUNCTION_GET_WIFI_ENCRYPTION = (byte)30;
  public final static byte FUNCTION_GET_WIFI_STATUS = (byte)31;
  public final static byte FUNCTION_REFRESH_WIFI_STATUS = (byte)32;
  public final static byte FUNCTION_SET_WIFI_CERTIFICATE = (byte)33;
  public final static byte FUNCTION_GET_WIFI_CERTIFICATE = (byte)34;
  public final static byte FUNCTION_SET_WIFI_POWER_MODE = (byte)35;
  public final static byte FUNCTION_GET_WIFI_POWER_MODE = (byte)36;
  public final static byte FUNCTION_GET_WIFI_BUFFER_INFO = (byte)37;
  public final static byte FUNCTION_SET_WIFI_REGULATORY_DOMAIN = (byte)38;
  public final static byte FUNCTION_GET_WIFI_REGULATORY_DOMAIN = (byte)39;
  public final static byte FUNCTION_GET_USB_VOLTAGE = (byte)40;
  public final static byte FUNCTION_SET_LONG_WIFI_KEY = (byte)41;
  public final static byte FUNCTION_GET_LONG_WIFI_KEY = (byte)42;
  public final static byte FUNCTION_SET_WIFI_HOSTNAME = (byte)43;
  public final static byte FUNCTION_GET_WIFI_HOSTNAME = (byte)44;
  public final static byte FUNCTION_SET_STACK_CURRENT_CALLBACK_PERIOD = (byte)45;
  public final static byte FUNCTION_GET_STACK_CURRENT_CALLBACK_PERIOD = (byte)46;
  public final static byte FUNCTION_SET_STACK_VOLTAGE_CALLBACK_PERIOD = (byte)47;
  public final static byte FUNCTION_GET_STACK_VOLTAGE_CALLBACK_PERIOD = (byte)48;
  public final static byte FUNCTION_SET_USB_VOLTAGE_CALLBACK_PERIOD = (byte)49;
  public final static byte FUNCTION_GET_USB_VOLTAGE_CALLBACK_PERIOD = (byte)50;
  public final static byte FUNCTION_SET_STACK_CURRENT_CALLBACK_THRESHOLD = (byte)51;
  public final static byte FUNCTION_GET_STACK_CURRENT_CALLBACK_THRESHOLD = (byte)52;
  public final static byte FUNCTION_SET_STACK_VOLTAGE_CALLBACK_THRESHOLD = (byte)53;
  public final static byte FUNCTION_GET_STACK_VOLTAGE_CALLBACK_THRESHOLD = (byte)54;
  public final static byte FUNCTION_SET_USB_VOLTAGE_CALLBACK_THRESHOLD = (byte)55;
  public final static byte FUNCTION_GET_USB_VOLTAGE_CALLBACK_THRESHOLD = (byte)56;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)57;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)58;
  public final static byte CALLBACK_STACK_CURRENT = (byte)59;
  public final static byte CALLBACK_STACK_VOLTAGE = (byte)60;
  public final static byte CALLBACK_USB_VOLTAGE = (byte)61;
  public final static byte CALLBACK_STACK_CURRENT_REACHED = (byte)62;
  public final static byte CALLBACK_STACK_VOLTAGE_REACHED = (byte)63;
  public final static byte CALLBACK_USB_VOLTAGE_REACHED = (byte)64;
  public final static byte FUNCTION_IS_ETHERNET_PRESENT = (byte)65;
  public final static byte FUNCTION_SET_ETHERNET_CONFIGURATION = (byte)66;
  public final static byte FUNCTION_GET_ETHERNET_CONFIGURATION = (byte)67;
  public final static byte FUNCTION_GET_ETHERNET_STATUS = (byte)68;
  public final static byte FUNCTION_SET_ETHERNET_HOSTNAME = (byte)69;
  public final static byte FUNCTION_SET_ETHERNET_MAC_ADDRESS = (byte)70;
  public final static byte FUNCTION_SET_ETHERNET_WEBSOCKET_CONFIGURATION = (byte)71;
  public final static byte FUNCTION_GET_ETHERNET_WEBSOCKET_CONFIGURATION = (byte)72;
  public final static byte FUNCTION_SET_ETHERNET_AUTHENTICATION_SECRET = (byte)73;
  public final static byte FUNCTION_GET_ETHERNET_AUTHENTICATION_SECRET = (byte)74;
  public final static byte FUNCTION_SET_WIFI_AUTHENTICATION_SECRET = (byte)75;
  public final static byte FUNCTION_GET_WIFI_AUTHENTICATION_SECRET = (byte)76;
  public final static byte FUNCTION_ENABLE_STATUS_LED = (byte)238;
  public final static byte FUNCTION_DISABLE_STATUS_LED = (byte)239;
  public final static byte FUNCTION_IS_STATUS_LED_ENABLED = (byte)240;
  public final static byte FUNCTION_GET_PROTOCOL1_BRICKLET_NAME = (byte)241;
  public final static byte FUNCTION_GET_CHIP_TEMPERATURE = (byte)242;
  public final static byte FUNCTION_RESET = (byte)243;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static long EXTENSION_TYPE_CHIBI = 1L;
  public final static long EXTENSION_TYPE_RS485 = 2L;
  public final static long EXTENSION_TYPE_WIFI = 3L;
  public final static long EXTENSION_TYPE_ETHERNET = 4L;
  public final static short CHIBI_FREQUENCY_OQPSK_868_MHZ = (short)0;
  public final static short CHIBI_FREQUENCY_OQPSK_915_MHZ = (short)1;
  public final static short CHIBI_FREQUENCY_OQPSK_780_MHZ = (short)2;
  public final static short CHIBI_FREQUENCY_BPSK40_915_MHZ = (short)3;
  public final static char RS485_PARITY_NONE = 'n';
  public final static char RS485_PARITY_EVEN = 'e';
  public final static char RS485_PARITY_ODD = 'o';
  public final static short WIFI_CONNECTION_DHCP = (short)0;
  public final static short WIFI_CONNECTION_STATIC_IP = (short)1;
  public final static short WIFI_CONNECTION_ACCESS_POINT_DHCP = (short)2;
  public final static short WIFI_CONNECTION_ACCESS_POINT_STATIC_IP = (short)3;
  public final static short WIFI_CONNECTION_AD_HOC_DHCP = (short)4;
  public final static short WIFI_CONNECTION_AD_HOC_STATIC_IP = (short)5;
  public final static short WIFI_ENCRYPTION_WPA_WPA2 = (short)0;
  public final static short WIFI_ENCRYPTION_WPA_ENTERPRISE = (short)1;
  public final static short WIFI_ENCRYPTION_WEP = (short)2;
  public final static short WIFI_ENCRYPTION_NO_ENCRYPTION = (short)3;
  public final static short WIFI_EAP_OPTION_OUTER_AUTH_EAP_FAST = (short)0;
  public final static short WIFI_EAP_OPTION_OUTER_AUTH_EAP_TLS = (short)1;
  public final static short WIFI_EAP_OPTION_OUTER_AUTH_EAP_TTLS = (short)2;
  public final static short WIFI_EAP_OPTION_OUTER_AUTH_EAP_PEAP = (short)3;
  public final static short WIFI_EAP_OPTION_INNER_AUTH_EAP_MSCHAP = (short)0;
  public final static short WIFI_EAP_OPTION_INNER_AUTH_EAP_GTC = (short)4;
  public final static short WIFI_EAP_OPTION_CERT_TYPE_CA_CERT = (short)0;
  public final static short WIFI_EAP_OPTION_CERT_TYPE_CLIENT_CERT = (short)8;
  public final static short WIFI_EAP_OPTION_CERT_TYPE_PRIVATE_KEY = (short)16;
  public final static short WIFI_STATE_DISASSOCIATED = (short)0;
  public final static short WIFI_STATE_ASSOCIATED = (short)1;
  public final static short WIFI_STATE_ASSOCIATING = (short)2;
  public final static short WIFI_STATE_ERROR = (short)3;
  public final static short WIFI_STATE_NOT_INITIALIZED_YET = (short)255;
  public final static short WIFI_POWER_MODE_FULL_SPEED = (short)0;
  public final static short WIFI_POWER_MODE_LOW_POWER = (short)1;
  public final static short WIFI_DOMAIN_CHANNEL_1TO11 = (short)0;
  public final static short WIFI_DOMAIN_CHANNEL_1TO13 = (short)1;
  public final static short WIFI_DOMAIN_CHANNEL_1TO14 = (short)2;
  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short ETHERNET_CONNECTION_DHCP = (short)0;
  public final static short ETHERNET_CONNECTION_STATIC_IP = (short)1;
  String uidString;
  private Buffer stackVoltageCallbackPeriod = getStackVoltageCallbackPeriodDefault();
  private Buffer wifiHostname = getWifiHostnameDefault();
  private Buffer stackVoltageCallbackThreshold = getStackVoltageCallbackThresholdDefault();
  private Buffer stackCurrentCallbackPeriod = getStackCurrentCallbackPeriodDefault();
  private Buffer uSBVoltageCallbackThreshold = getUSBVoltageCallbackThresholdDefault();
  private Buffer chibiAddress = getChibiAddressDefault();
  private Buffer rS485Address = getRS485AddressDefault();
  private Buffer chibiFrequency = getChibiFrequencyDefault();
  private Buffer wifiConfiguration = getWifiConfigurationDefault();
  private Buffer ethernetWebsocketConfiguration = getEthernetWebsocketConfigurationDefault();
  private Buffer wifiEncryption = getWifiEncryptionDefault();
  private Buffer wifiRegulatoryDomain = getWifiRegulatoryDomainDefault();
  private Buffer debouncePeriod = getDebouncePeriodDefault();
  private Buffer ethernetConfiguration = getEthernetConfigurationDefault();
  private Buffer ethernetAuthenticationSecret = getEthernetAuthenticationSecretDefault();
  private Buffer uSBVoltageCallbackPeriod = getUSBVoltageCallbackPeriodDefault();
  private Buffer extensionType = getExtensionTypeDefault();
  private Buffer longWifiKey = getLongWifiKeyDefault();
  private Buffer chibiMasterAddress = getChibiMasterAddressDefault();
  private Buffer chibiChannel = getChibiChannelDefault();
  private Buffer chibiSlaveAddress = getChibiSlaveAddressDefault();
  private Buffer wifiPowerMode = getWifiPowerModeDefault();
  private Buffer wifiAuthenticationSecret = getWifiAuthenticationSecretDefault();
  private Buffer rS485SlaveAddress = getRS485SlaveAddressDefault();
  private Buffer rS485Configuration = getRS485ConfigurationDefault();
  private Buffer StatusLED = isStatusLEDEnabledDefault();
  private Buffer wifiCertificate = getWifiCertificateDefault();
  private Buffer stackCurrentCallbackThreshold = getStackCurrentCallbackThresholdDefault();

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 3;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickMaster.class);
    uidString = config().getString("uid");
    uidBytes = Utils.uid2long(uidString);

    vertx.eventBus().consumer(uidString, message -> {
      Buffer msgBuffer = (Buffer) message.body();
      Packet packet = new Packet(msgBuffer);
      logger.trace("got request: {}", packet.toString());
      Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
      for (Object handlerid : handlerids) {
        Buffer buffer = callFunction(packet);
        // TODO add logging
        if (packet.getResponseExpected()) {
            if (buffer != null) {
              logger.trace(
                  "sending answer: {}", new Packet(buffer).toString());
              vertx.eventBus().publish((String) handlerid, buffer);
            } else {
              logger.trace("buffer is null");
            }
        }
      }
      });

    // broadcast queue for enumeration requests
    vertx.eventBus().consumer(
        CommonServices.BROADCAST_UID,
        message -> {
          Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
          if (handlerids != null) {
            logger.debug("sending enumerate answer");
            for (Object handlerid : handlerids) {
              vertx.eventBus().publish((String) handlerid,
                  Utils.getEnumerateResponse(uidString, uidBytes, DEVICE_IDENTIFIER));
            }
          } else {
            logger.error("no handlerids found");
          }
        });

  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_STACK_VOLTAGE) {
      buffer = getStackVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_STACK_CURRENT) {
      buffer = getStackCurrent(packet);
    }
    else if (functionId == FUNCTION_SET_EXTENSION_TYPE) {
      buffer = setExtensionType(packet);
    }
    else if (functionId == FUNCTION_GET_EXTENSION_TYPE) {
      buffer = getExtensionType(packet);
    }
    else if (functionId == FUNCTION_IS_CHIBI_PRESENT) {
      buffer = isChibiPresent(packet);
    }
    else if (functionId == FUNCTION_SET_CHIBI_ADDRESS) {
      buffer = setChibiAddress(packet);
    }
    else if (functionId == FUNCTION_GET_CHIBI_ADDRESS) {
      buffer = getChibiAddress(packet);
    }
    else if (functionId == FUNCTION_SET_CHIBI_MASTER_ADDRESS) {
      buffer = setChibiMasterAddress(packet);
    }
    else if (functionId == FUNCTION_GET_CHIBI_MASTER_ADDRESS) {
      buffer = getChibiMasterAddress(packet);
    }
    else if (functionId == FUNCTION_SET_CHIBI_SLAVE_ADDRESS) {
      buffer = setChibiSlaveAddress(packet);
    }
    else if (functionId == FUNCTION_GET_CHIBI_SLAVE_ADDRESS) {
      buffer = getChibiSlaveAddress(packet);
    }
    else if (functionId == FUNCTION_GET_CHIBI_SIGNAL_STRENGTH) {
      buffer = getChibiSignalStrength(packet);
    }
    else if (functionId == FUNCTION_GET_CHIBI_ERROR_LOG) {
      buffer = getChibiErrorLog(packet);
    }
    else if (functionId == FUNCTION_SET_CHIBI_FREQUENCY) {
      buffer = setChibiFrequency(packet);
    }
    else if (functionId == FUNCTION_GET_CHIBI_FREQUENCY) {
      buffer = getChibiFrequency(packet);
    }
    else if (functionId == FUNCTION_SET_CHIBI_CHANNEL) {
      buffer = setChibiChannel(packet);
    }
    else if (functionId == FUNCTION_GET_CHIBI_CHANNEL) {
      buffer = getChibiChannel(packet);
    }
    else if (functionId == FUNCTION_IS_RS485_PRESENT) {
      buffer = isRS485Present(packet);
    }
    else if (functionId == FUNCTION_SET_RS485_ADDRESS) {
      buffer = setRS485Address(packet);
    }
    else if (functionId == FUNCTION_GET_RS485_ADDRESS) {
      buffer = getRS485Address(packet);
    }
    else if (functionId == FUNCTION_SET_RS485_SLAVE_ADDRESS) {
      buffer = setRS485SlaveAddress(packet);
    }
    else if (functionId == FUNCTION_GET_RS485_SLAVE_ADDRESS) {
      buffer = getRS485SlaveAddress(packet);
    }
    else if (functionId == FUNCTION_GET_RS485_ERROR_LOG) {
      buffer = getRS485ErrorLog(packet);
    }
    else if (functionId == FUNCTION_SET_RS485_CONFIGURATION) {
      buffer = setRS485Configuration(packet);
    }
    else if (functionId == FUNCTION_GET_RS485_CONFIGURATION) {
      buffer = getRS485Configuration(packet);
    }
    else if (functionId == FUNCTION_IS_WIFI_PRESENT) {
      buffer = isWifiPresent(packet);
    }
    else if (functionId == FUNCTION_SET_WIFI_CONFIGURATION) {
      buffer = setWifiConfiguration(packet);
    }
    else if (functionId == FUNCTION_GET_WIFI_CONFIGURATION) {
      buffer = getWifiConfiguration(packet);
    }
    else if (functionId == FUNCTION_SET_WIFI_ENCRYPTION) {
      buffer = setWifiEncryption(packet);
    }
    else if (functionId == FUNCTION_GET_WIFI_ENCRYPTION) {
      buffer = getWifiEncryption(packet);
    }
    else if (functionId == FUNCTION_GET_WIFI_STATUS) {
      buffer = getWifiStatus(packet);
    }
    else if (functionId == FUNCTION_REFRESH_WIFI_STATUS) {
      buffer = refreshWifiStatus(packet);
    }
    else if (functionId == FUNCTION_SET_WIFI_CERTIFICATE) {
      buffer = setWifiCertificate(packet);
    }
    else if (functionId == FUNCTION_GET_WIFI_CERTIFICATE) {
      buffer = getWifiCertificate(packet);
    }
    else if (functionId == FUNCTION_SET_WIFI_POWER_MODE) {
      buffer = setWifiPowerMode(packet);
    }
    else if (functionId == FUNCTION_GET_WIFI_POWER_MODE) {
      buffer = getWifiPowerMode(packet);
    }
    else if (functionId == FUNCTION_GET_WIFI_BUFFER_INFO) {
      buffer = getWifiBufferInfo(packet);
    }
    else if (functionId == FUNCTION_SET_WIFI_REGULATORY_DOMAIN) {
      buffer = setWifiRegulatoryDomain(packet);
    }
    else if (functionId == FUNCTION_GET_WIFI_REGULATORY_DOMAIN) {
      buffer = getWifiRegulatoryDomain(packet);
    }
    else if (functionId == FUNCTION_GET_USB_VOLTAGE) {
      buffer = getUSBVoltage(packet);
    }
    else if (functionId == FUNCTION_SET_LONG_WIFI_KEY) {
      buffer = setLongWifiKey(packet);
    }
    else if (functionId == FUNCTION_GET_LONG_WIFI_KEY) {
      buffer = getLongWifiKey(packet);
    }
    else if (functionId == FUNCTION_SET_WIFI_HOSTNAME) {
      buffer = setWifiHostname(packet);
    }
    else if (functionId == FUNCTION_GET_WIFI_HOSTNAME) {
      buffer = getWifiHostname(packet);
    }
    else if (functionId == FUNCTION_SET_STACK_CURRENT_CALLBACK_PERIOD) {
      buffer = setStackCurrentCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_STACK_CURRENT_CALLBACK_PERIOD) {
      buffer = getStackCurrentCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_STACK_VOLTAGE_CALLBACK_PERIOD) {
      buffer = setStackVoltageCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_STACK_VOLTAGE_CALLBACK_PERIOD) {
      buffer = getStackVoltageCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_USB_VOLTAGE_CALLBACK_PERIOD) {
      buffer = setUSBVoltageCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_USB_VOLTAGE_CALLBACK_PERIOD) {
      buffer = getUSBVoltageCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_STACK_CURRENT_CALLBACK_THRESHOLD) {
      buffer = setStackCurrentCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_STACK_CURRENT_CALLBACK_THRESHOLD) {
      buffer = getStackCurrentCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_STACK_VOLTAGE_CALLBACK_THRESHOLD) {
      buffer = setStackVoltageCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_STACK_VOLTAGE_CALLBACK_THRESHOLD) {
      buffer = getStackVoltageCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_USB_VOLTAGE_CALLBACK_THRESHOLD) {
      buffer = setUSBVoltageCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_USB_VOLTAGE_CALLBACK_THRESHOLD) {
      buffer = getUSBVoltageCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_IS_ETHERNET_PRESENT) {
      buffer = isEthernetPresent(packet);
    }
    else if (functionId == FUNCTION_SET_ETHERNET_CONFIGURATION) {
      buffer = setEthernetConfiguration(packet);
    }
    else if (functionId == FUNCTION_GET_ETHERNET_CONFIGURATION) {
      buffer = getEthernetConfiguration(packet);
    }
    else if (functionId == FUNCTION_GET_ETHERNET_STATUS) {
      buffer = getEthernetStatus(packet);
    }
    else if (functionId == FUNCTION_SET_ETHERNET_HOSTNAME) {
      buffer = setEthernetHostname(packet);
    }
    else if (functionId == FUNCTION_SET_ETHERNET_MAC_ADDRESS) {
      buffer = setEthernetMACAddress(packet);
    }
    else if (functionId == FUNCTION_SET_ETHERNET_WEBSOCKET_CONFIGURATION) {
      buffer = setEthernetWebsocketConfiguration(packet);
    }
    else if (functionId == FUNCTION_GET_ETHERNET_WEBSOCKET_CONFIGURATION) {
      buffer = getEthernetWebsocketConfiguration(packet);
    }
    else if (functionId == FUNCTION_SET_ETHERNET_AUTHENTICATION_SECRET) {
      buffer = setEthernetAuthenticationSecret(packet);
    }
    else if (functionId == FUNCTION_GET_ETHERNET_AUTHENTICATION_SECRET) {
      buffer = getEthernetAuthenticationSecret(packet);
    }
    else if (functionId == FUNCTION_SET_WIFI_AUTHENTICATION_SECRET) {
      buffer = setWifiAuthenticationSecret(packet);
    }
    else if (functionId == FUNCTION_GET_WIFI_AUTHENTICATION_SECRET) {
      buffer = getWifiAuthenticationSecret(packet);
    }
    else if (functionId == FUNCTION_ENABLE_STATUS_LED) {
      buffer = enableStatusLED(packet);
    }
    else if (functionId == FUNCTION_DISABLE_STATUS_LED) {
      buffer = disableStatusLED(packet);
    }
    else if (functionId == FUNCTION_IS_STATUS_LED_ENABLED) {
      buffer = isStatusLEDEnabled(packet);
    }
    else if (functionId == FUNCTION_GET_CHIP_TEMPERATURE) {
      buffer = getChipTemperature(packet);
    }
    else if (functionId == FUNCTION_RESET) {
      buffer = reset(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  /**
   * 
   */
  private Buffer getUSBVoltage(Packet packet) {
    logger.debug("function getUSBVoltage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_USB_VOLTAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getWifiStatus(Packet packet) {
    logger.debug("function getWifiStatus");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 36;
      byte functionId = FUNCTION_GET_WIFI_STATUS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(6));        
      buffer.appendBytes(Utils.get1ByteURandomValue(6));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getChibiErrorLog(Packet packet) {
    logger.debug("function getChibiErrorLog");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 8;
      byte functionId = FUNCTION_GET_CHIBI_ERROR_LOG;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getWifiBufferInfo(Packet packet) {
    logger.debug("function getWifiBufferInfo");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 8;
      byte functionId = FUNCTION_GET_WIFI_BUFFER_INFO;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getStackVoltage(Packet packet) {
    logger.debug("function getStackVoltage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_STACK_VOLTAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getChibiSignalStrength(Packet packet) {
    logger.debug("function getChibiSignalStrength");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_CHIBI_SIGNAL_STRENGTH;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getRS485ErrorLog(Packet packet) {
    logger.debug("function getRS485ErrorLog");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_RS485_ERROR_LOG;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getEthernetStatus(Packet packet) {
    logger.debug("function getEthernetStatus");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 58;
      byte functionId = FUNCTION_GET_ETHERNET_STATUS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(6));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.getCharRandomValue(32));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getChipTemperature(Packet packet) {
    logger.debug("function getChipTemperature");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_CHIP_TEMPERATURE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getStackCurrent(Packet packet) {
    logger.debug("function getStackCurrent");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_STACK_CURRENT;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getStackVoltageCallbackPeriod(Packet packet) {
    logger.debug("function getStackVoltageCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_STACK_VOLTAGE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.stackVoltageCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getStackVoltageCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getWifiHostname(Packet packet) {
    logger.debug("function getWifiHostname");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 16;
      byte functionId = FUNCTION_GET_WIFI_HOSTNAME;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.wifiHostname);
      return buffer;
    }

    return null;
  }

  private Buffer getWifiHostnameDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(16));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getStackVoltageCallbackThreshold(Packet packet) {
    logger.debug("function getStackVoltageCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_STACK_VOLTAGE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.stackVoltageCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getStackVoltageCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getStackCurrentCallbackPeriod(Packet packet) {
    logger.debug("function getStackCurrentCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_STACK_CURRENT_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.stackCurrentCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getStackCurrentCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getUSBVoltageCallbackThreshold(Packet packet) {
    logger.debug("function getUSBVoltageCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_USB_VOLTAGE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.uSBVoltageCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getUSBVoltageCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getChibiAddress(Packet packet) {
    logger.debug("function getChibiAddress");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_CHIBI_ADDRESS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.chibiAddress);
      return buffer;
    }

    return null;
  }

  private Buffer getChibiAddressDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getRS485Address(Packet packet) {
    logger.debug("function getRS485Address");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_RS485_ADDRESS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.rS485Address);
      return buffer;
    }

    return null;
  }

  private Buffer getRS485AddressDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getChibiFrequency(Packet packet) {
    logger.debug("function getChibiFrequency");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_CHIBI_FREQUENCY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.chibiFrequency);
      return buffer;
    }

    return null;
  }

  private Buffer getChibiFrequencyDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getWifiConfiguration(Packet packet) {
    logger.debug("function getWifiConfiguration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 47;
      byte functionId = FUNCTION_GET_WIFI_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.wifiConfiguration);
      return buffer;
    }

    return null;
  }

  private Buffer getWifiConfigurationDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(32));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getEthernetWebsocketConfiguration(Packet packet) {
    logger.debug("function getEthernetWebsocketConfiguration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_ETHERNET_WEBSOCKET_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.ethernetWebsocketConfiguration);
      return buffer;
    }

    return null;
  }

  private Buffer getEthernetWebsocketConfigurationDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getWifiEncryption(Packet packet) {
    logger.debug("function getWifiEncryption");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 59;
      byte functionId = FUNCTION_GET_WIFI_ENCRYPTION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.wifiEncryption);
      return buffer;
    }

    return null;
  }

  private Buffer getWifiEncryptionDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.getCharRandomValue(50));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getWifiRegulatoryDomain(Packet packet) {
    logger.debug("function getWifiRegulatoryDomain");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_WIFI_REGULATORY_DOMAIN;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.wifiRegulatoryDomain);
      return buffer;
    }

    return null;
  }

  private Buffer getWifiRegulatoryDomainDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getDebouncePeriod(Packet packet) {
    logger.debug("function getDebouncePeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_DEBOUNCE_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.debouncePeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getDebouncePeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getEthernetConfiguration(Packet packet) {
    logger.debug("function getEthernetConfiguration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 15;
      byte functionId = FUNCTION_GET_ETHERNET_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.ethernetConfiguration);
      return buffer;
    }

    return null;
  }

  private Buffer getEthernetConfigurationDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get1ByteURandomValue(4));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getEthernetAuthenticationSecret(Packet packet) {
    logger.debug("function getEthernetAuthenticationSecret");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 64;
      byte functionId = FUNCTION_GET_ETHERNET_AUTHENTICATION_SECRET;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.ethernetAuthenticationSecret);
      return buffer;
    }

    return null;
  }

  private Buffer getEthernetAuthenticationSecretDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(64));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getUSBVoltageCallbackPeriod(Packet packet) {
    logger.debug("function getUSBVoltageCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_USB_VOLTAGE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.uSBVoltageCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getUSBVoltageCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getExtensionType(Packet packet) {
    logger.debug("function getExtensionType");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_EXTENSION_TYPE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.extensionType);
      return buffer;
    }

    return null;
  }

  private Buffer getExtensionTypeDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getLongWifiKey(Packet packet) {
    logger.debug("function getLongWifiKey");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 64;
      byte functionId = FUNCTION_GET_LONG_WIFI_KEY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.longWifiKey);
      return buffer;
    }

    return null;
  }

  private Buffer getLongWifiKeyDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(64));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getChibiMasterAddress(Packet packet) {
    logger.debug("function getChibiMasterAddress");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_CHIBI_MASTER_ADDRESS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.chibiMasterAddress);
      return buffer;
    }

    return null;
  }

  private Buffer getChibiMasterAddressDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getChibiChannel(Packet packet) {
    logger.debug("function getChibiChannel");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_CHIBI_CHANNEL;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.chibiChannel);
      return buffer;
    }

    return null;
  }

  private Buffer getChibiChannelDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getChibiSlaveAddress(Packet packet) {
    logger.debug("function getChibiSlaveAddress");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_CHIBI_SLAVE_ADDRESS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.chibiSlaveAddress);
      return buffer;
    }

    return null;
  }

  private Buffer getChibiSlaveAddressDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getWifiPowerMode(Packet packet) {
    logger.debug("function getWifiPowerMode");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_WIFI_POWER_MODE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.wifiPowerMode);
      return buffer;
    }

    return null;
  }

  private Buffer getWifiPowerModeDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getWifiAuthenticationSecret(Packet packet) {
    logger.debug("function getWifiAuthenticationSecret");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 64;
      byte functionId = FUNCTION_GET_WIFI_AUTHENTICATION_SECRET;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.wifiAuthenticationSecret);
      return buffer;
    }

    return null;
  }

  private Buffer getWifiAuthenticationSecretDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(64));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getRS485SlaveAddress(Packet packet) {
    logger.debug("function getRS485SlaveAddress");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_RS485_SLAVE_ADDRESS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.rS485SlaveAddress);
      return buffer;
    }

    return null;
  }

  private Buffer getRS485SlaveAddressDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getRS485Configuration(Packet packet) {
    logger.debug("function getRS485Configuration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 6;
      byte functionId = FUNCTION_GET_RS485_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.rS485Configuration);
      return buffer;
    }

    return null;
  }

  private Buffer getRS485ConfigurationDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer isStatusLEDEnabled(Packet packet) {
    logger.debug("function isStatusLEDEnabled");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_IS_STATUS_LED_ENABLED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.StatusLED);
      return buffer;
    }

    return null;
  }

  private Buffer isStatusLEDEnabledDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getBoolRandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getWifiCertificate(Packet packet) {
    logger.debug("function getWifiCertificate");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 33;
      byte functionId = FUNCTION_GET_WIFI_CERTIFICATE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.wifiCertificate);
      return buffer;
    }

    return null;
  }

  private Buffer getWifiCertificateDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(32));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getStackCurrentCallbackThreshold(Packet packet) {
    logger.debug("function getStackCurrentCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_STACK_CURRENT_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.stackCurrentCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getStackCurrentCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer setEthernetConfiguration(Packet packet) {
    logger.debug("function setEthernetConfiguration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ETHERNET_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.ethernetConfiguration = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setWifiHostname(Packet packet) {
    logger.debug("function setWifiHostname");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_WIFI_HOSTNAME;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.wifiHostname = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setWifiAuthenticationSecret(Packet packet) {
    logger.debug("function setWifiAuthenticationSecret");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_WIFI_AUTHENTICATION_SECRET;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.wifiAuthenticationSecret = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setChibiAddress(Packet packet) {
    logger.debug("function setChibiAddress");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CHIBI_ADDRESS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.chibiAddress = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setChibiSlaveAddress(Packet packet) {
    logger.debug("function setChibiSlaveAddress");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CHIBI_SLAVE_ADDRESS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.chibiSlaveAddress = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setRS485Address(Packet packet) {
    logger.debug("function setRS485Address");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_RS485_ADDRESS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.rS485Address = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setUSBVoltageCallbackPeriod(Packet packet) {
    logger.debug("function setUSBVoltageCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_USB_VOLTAGE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.uSBVoltageCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setStackVoltageCallbackThreshold(Packet packet) {
    logger.debug("function setStackVoltageCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_STACK_VOLTAGE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.stackVoltageCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setRS485Configuration(Packet packet) {
    logger.debug("function setRS485Configuration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_RS485_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.rS485Configuration = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setWifiConfiguration(Packet packet) {
    logger.debug("function setWifiConfiguration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_WIFI_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.wifiConfiguration = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setRS485SlaveAddress(Packet packet) {
    logger.debug("function setRS485SlaveAddress");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_RS485_SLAVE_ADDRESS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.rS485SlaveAddress = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setStackCurrentCallbackThreshold(Packet packet) {
    logger.debug("function setStackCurrentCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_STACK_CURRENT_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.stackCurrentCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer enableStatusLED(Packet packet) {
    logger.debug("function enableStatusLED");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_ENABLE_STATUS_LED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.StatusLED = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setWifiPowerMode(Packet packet) {
    logger.debug("function setWifiPowerMode");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_WIFI_POWER_MODE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.wifiPowerMode = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setEthernetWebsocketConfiguration(Packet packet) {
    logger.debug("function setEthernetWebsocketConfiguration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ETHERNET_WEBSOCKET_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.ethernetWebsocketConfiguration = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setEthernetAuthenticationSecret(Packet packet) {
    logger.debug("function setEthernetAuthenticationSecret");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ETHERNET_AUTHENTICATION_SECRET;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.ethernetAuthenticationSecret = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setDebouncePeriod(Packet packet) {
    logger.debug("function setDebouncePeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DEBOUNCE_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.debouncePeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setChibiMasterAddress(Packet packet) {
    logger.debug("function setChibiMasterAddress");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CHIBI_MASTER_ADDRESS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.chibiMasterAddress = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setChibiChannel(Packet packet) {
    logger.debug("function setChibiChannel");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CHIBI_CHANNEL;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.chibiChannel = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setExtensionType(Packet packet) {
    logger.debug("function setExtensionType");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_EXTENSION_TYPE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.extensionType = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setWifiCertificate(Packet packet) {
    logger.debug("function setWifiCertificate");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_WIFI_CERTIFICATE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.wifiCertificate = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setUSBVoltageCallbackThreshold(Packet packet) {
    logger.debug("function setUSBVoltageCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_USB_VOLTAGE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.uSBVoltageCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setStackCurrentCallbackPeriod(Packet packet) {
    logger.debug("function setStackCurrentCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_STACK_CURRENT_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.stackCurrentCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setWifiRegulatoryDomain(Packet packet) {
    logger.debug("function setWifiRegulatoryDomain");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_WIFI_REGULATORY_DOMAIN;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.wifiRegulatoryDomain = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setChibiFrequency(Packet packet) {
    logger.debug("function setChibiFrequency");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CHIBI_FREQUENCY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.chibiFrequency = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setStackVoltageCallbackPeriod(Packet packet) {
    logger.debug("function setStackVoltageCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_STACK_VOLTAGE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.stackVoltageCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setLongWifiKey(Packet packet) {
    logger.debug("function setLongWifiKey");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_LONG_WIFI_KEY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.longWifiKey = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setWifiEncryption(Packet packet) {
    logger.debug("function setWifiEncryption");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_WIFI_ENCRYPTION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.wifiEncryption = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer disableStatusLED(Packet packet) {
    logger.debug("function disableStatusLED");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_DISABLE_STATUS_LED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.StatusLED = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer getIdentity(Packet packet) {
    logger.debug("function getIdentity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 25;
      byte functionId = FUNCTION_GET_IDENTITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
       buffer.appendBuffer(Utils.getIdentityPayload(uidString, uidBytes, DEVICE_IDENTIFIER));
      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer reset(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer isChibiPresent(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer isEthernetPresent(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer setEthernetHostname(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer setEthernetMACAddress(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer isRS485Present(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer refreshWifiStatus(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer isWifiPresent(Packet packet) {
    //TODO dummy method
    return null;
  }
}
