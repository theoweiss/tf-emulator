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
import org.m1theo.tfemulator.Utils.Step;
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

  private Buffer wifiHostname = getWifiHostnameDefault();
        
  private Buffer stackVoltageCallbackThreshold = getStackVoltageCallbackThresholdDefault();
        
  private Buffer chibiAddress = getChibiAddressDefault();
        
  private Buffer rS485Address = getRS485AddressDefault();
        
  private Buffer uSBVoltageCallbackThreshold = getUSBVoltageCallbackThresholdDefault();
        
  private Buffer wifiEncryption = getWifiEncryptionDefault();
        
  private Buffer wifiRegulatoryDomain = getWifiRegulatoryDomainDefault();
        
  private Buffer extensionType = getExtensionTypeDefault();
        
  private Buffer chibiChannel = getChibiChannelDefault();
        
  private Buffer StatusLED = getStatusLEDDefault();
        
  private Buffer rS485Configuration = getRS485ConfigurationDefault();
        
  private Buffer wifiConfiguration = getWifiConfigurationDefault();
        
  private Buffer ethernetWebsocketConfiguration = getEthernetWebsocketConfigurationDefault();
        
  private Buffer debouncePeriod = getDebouncePeriodDefault();
        
  private Buffer ethernetConfiguration = getEthernetConfigurationDefault();
        
  private Buffer ethernetAuthenticationSecret = getEthernetAuthenticationSecretDefault();
        
  private Buffer chibiFrequency = getChibiFrequencyDefault();
        
  private Buffer longWifiKey = getLongWifiKeyDefault();
        
  private Buffer chibiMasterAddress = getChibiMasterAddressDefault();
        
  private Buffer chibiSlaveAddress = getChibiSlaveAddressDefault();
        
  private Buffer wifiPowerMode = getWifiPowerModeDefault();
        
  private Buffer wifiAuthenticationSecret = getWifiAuthenticationSecretDefault();
        
  private Buffer rS485SlaveAddress = getRS485SlaveAddressDefault();
        
  private Buffer wifiCertificate = getWifiCertificateDefault();
        
  private Buffer stackCurrentCallbackThreshold = getStackCurrentCallbackThresholdDefault();
        
  private short uSBVoltage = 100;
  private short uSBVoltage_max = 1000;
  private short uSBVoltage_min = 0;
  private short uSBVoltage_step = 1;
  private long uSBVoltage_generator_period = 100;
  private Step uSBVoltage_direction = Step.UP;
  private long uSBVoltageCallbackPeriod;
  private long uSBVoltage_callback_id;
  private short uSBVoltage_last_value_called_back;

  private byte wifiStatus = 100;
  private byte wifiStatus_max = 1000;
  private byte wifiStatus_min = 0;
  private byte wifiStatus_step = 1;
  private long wifiStatus_generator_period = 100;
  private Step wifiStatus_direction = Step.UP;
  private long wifiStatusCallbackPeriod;
  private long wifiStatus_callback_id;
  private byte wifiStatus_last_value_called_back;

  private short chibiErrorLog = 100;
  private short chibiErrorLog_max = 1000;
  private short chibiErrorLog_min = 0;
  private short chibiErrorLog_step = 1;
  private long chibiErrorLog_generator_period = 100;
  private Step chibiErrorLog_direction = Step.UP;
  private long chibiErrorLogCallbackPeriod;
  private long chibiErrorLog_callback_id;
  private short chibiErrorLog_last_value_called_back;

  private int wifiBufferInfo = 100;
  private int wifiBufferInfo_max = 1000;
  private int wifiBufferInfo_min = 0;
  private int wifiBufferInfo_step = 1;
  private long wifiBufferInfo_generator_period = 100;
  private Step wifiBufferInfo_direction = Step.UP;
  private long wifiBufferInfoCallbackPeriod;
  private long wifiBufferInfo_callback_id;
  private int wifiBufferInfo_last_value_called_back;

  private short stackVoltage = 100;
  private short stackVoltage_max = 1000;
  private short stackVoltage_min = 0;
  private short stackVoltage_step = 1;
  private long stackVoltage_generator_period = 100;
  private Step stackVoltage_direction = Step.UP;
  private long stackVoltageCallbackPeriod;
  private long stackVoltage_callback_id;
  private short stackVoltage_last_value_called_back;

  private byte chibiSignalStrength = 100;
  private byte chibiSignalStrength_max = 1000;
  private byte chibiSignalStrength_min = 0;
  private byte chibiSignalStrength_step = 1;
  private long chibiSignalStrength_generator_period = 100;
  private Step chibiSignalStrength_direction = Step.UP;
  private long chibiSignalStrengthCallbackPeriod;
  private long chibiSignalStrength_callback_id;
  private byte chibiSignalStrength_last_value_called_back;

  private short rS485ErrorLog = 100;
  private short rS485ErrorLog_max = 1000;
  private short rS485ErrorLog_min = 0;
  private short rS485ErrorLog_step = 1;
  private long rS485ErrorLog_generator_period = 100;
  private Step rS485ErrorLog_direction = Step.UP;
  private long rS485ErrorLogCallbackPeriod;
  private long rS485ErrorLog_callback_id;
  private short rS485ErrorLog_last_value_called_back;

  private byte ethernetStatus = 100;
  private byte ethernetStatus_max = 1000;
  private byte ethernetStatus_min = 0;
  private byte ethernetStatus_step = 1;
  private long ethernetStatus_generator_period = 100;
  private Step ethernetStatus_direction = Step.UP;
  private long ethernetStatusCallbackPeriod;
  private long ethernetStatus_callback_id;
  private byte ethernetStatus_last_value_called_back;

  private short chipTemperature = 100;
  private short chipTemperature_max = 1000;
  private short chipTemperature_min = 0;
  private short chipTemperature_step = 1;
  private long chipTemperature_generator_period = 100;
  private Step chipTemperature_direction = Step.UP;
  private long chipTemperatureCallbackPeriod;
  private long chipTemperature_callback_id;
  private short chipTemperature_last_value_called_back;

  private short stackCurrent = 100;
  private short stackCurrent_max = 1000;
  private short stackCurrent_min = 0;
  private short stackCurrent_step = 1;
  private long stackCurrent_generator_period = 100;
  private Step stackCurrent_direction = Step.UP;
  private long stackCurrentCallbackPeriod;
  private long stackCurrent_callback_id;
  private short stackCurrent_last_value_called_back;

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

    startUSBVoltageGenerator();
    startWifiStatusGenerator();
    startChibiErrorLogGenerator();
    startWifiBufferInfoGenerator();
    startStackVoltageGenerator();
    startChibiSignalStrengthGenerator();
    startRS485ErrorLogGenerator();
    startEthernetStatusGenerator();
    startChipTemperatureGenerator();
    startStackCurrentGenerator();
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


  private void startUSBVoltageGenerator() {
    if (uSBVoltage_step == 0) {
      return;
    }
    vertx.setPeriodic(uSBVoltage_generator_period, id -> {
      if (uSBVoltage_direction == Step.UP) {
        if (uSBVoltage >= uSBVoltage_max) {
          uSBVoltage_direction = Step.DOWN;
          this.uSBVoltage = (short) (uSBVoltage - uSBVoltage_step);
        } else {
          this.uSBVoltage = (short) (uSBVoltage + uSBVoltage_step);
        }
      } else {
        if (uSBVoltage <= uSBVoltage_min) {
          uSBVoltage_direction = Step.UP;
          this.uSBVoltage = (short) (uSBVoltage + uSBVoltage_step);
        } else {
          this.uSBVoltage = (short) (uSBVoltage - uSBVoltage_step);
        }
      }
    });
  }
        
  private void startWifiStatusGenerator() {
    if (wifiStatus_step == 0) {
      return;
    }
    vertx.setPeriodic(wifiStatus_generator_period, id -> {
      if (wifiStatus_direction == Step.UP) {
        if (wifiStatus >= wifiStatus_max) {
          wifiStatus_direction = Step.DOWN;
          this.wifiStatus = (byte) (wifiStatus - wifiStatus_step);
        } else {
          this.wifiStatus = (byte) (wifiStatus + wifiStatus_step);
        }
      } else {
        if (wifiStatus <= wifiStatus_min) {
          wifiStatus_direction = Step.UP;
          this.wifiStatus = (byte) (wifiStatus + wifiStatus_step);
        } else {
          this.wifiStatus = (byte) (wifiStatus - wifiStatus_step);
        }
      }
    });
  }
        
  private void startChibiErrorLogGenerator() {
    if (chibiErrorLog_step == 0) {
      return;
    }
    vertx.setPeriodic(chibiErrorLog_generator_period, id -> {
      if (chibiErrorLog_direction == Step.UP) {
        if (chibiErrorLog >= chibiErrorLog_max) {
          chibiErrorLog_direction = Step.DOWN;
          this.chibiErrorLog = (short) (chibiErrorLog - chibiErrorLog_step);
        } else {
          this.chibiErrorLog = (short) (chibiErrorLog + chibiErrorLog_step);
        }
      } else {
        if (chibiErrorLog <= chibiErrorLog_min) {
          chibiErrorLog_direction = Step.UP;
          this.chibiErrorLog = (short) (chibiErrorLog + chibiErrorLog_step);
        } else {
          this.chibiErrorLog = (short) (chibiErrorLog - chibiErrorLog_step);
        }
      }
    });
  }
        
  private void startWifiBufferInfoGenerator() {
    if (wifiBufferInfo_step == 0) {
      return;
    }
    vertx.setPeriodic(wifiBufferInfo_generator_period, id -> {
      if (wifiBufferInfo_direction == Step.UP) {
        if (wifiBufferInfo >= wifiBufferInfo_max) {
          wifiBufferInfo_direction = Step.DOWN;
          this.wifiBufferInfo = (int) (wifiBufferInfo - wifiBufferInfo_step);
        } else {
          this.wifiBufferInfo = (int) (wifiBufferInfo + wifiBufferInfo_step);
        }
      } else {
        if (wifiBufferInfo <= wifiBufferInfo_min) {
          wifiBufferInfo_direction = Step.UP;
          this.wifiBufferInfo = (int) (wifiBufferInfo + wifiBufferInfo_step);
        } else {
          this.wifiBufferInfo = (int) (wifiBufferInfo - wifiBufferInfo_step);
        }
      }
    });
  }
        
  private void startStackVoltageGenerator() {
    if (stackVoltage_step == 0) {
      return;
    }
    vertx.setPeriodic(stackVoltage_generator_period, id -> {
      if (stackVoltage_direction == Step.UP) {
        if (stackVoltage >= stackVoltage_max) {
          stackVoltage_direction = Step.DOWN;
          this.stackVoltage = (short) (stackVoltage - stackVoltage_step);
        } else {
          this.stackVoltage = (short) (stackVoltage + stackVoltage_step);
        }
      } else {
        if (stackVoltage <= stackVoltage_min) {
          stackVoltage_direction = Step.UP;
          this.stackVoltage = (short) (stackVoltage + stackVoltage_step);
        } else {
          this.stackVoltage = (short) (stackVoltage - stackVoltage_step);
        }
      }
    });
  }
        
  private void startChibiSignalStrengthGenerator() {
    if (chibiSignalStrength_step == 0) {
      return;
    }
    vertx.setPeriodic(chibiSignalStrength_generator_period, id -> {
      if (chibiSignalStrength_direction == Step.UP) {
        if (chibiSignalStrength >= chibiSignalStrength_max) {
          chibiSignalStrength_direction = Step.DOWN;
          this.chibiSignalStrength = (byte) (chibiSignalStrength - chibiSignalStrength_step);
        } else {
          this.chibiSignalStrength = (byte) (chibiSignalStrength + chibiSignalStrength_step);
        }
      } else {
        if (chibiSignalStrength <= chibiSignalStrength_min) {
          chibiSignalStrength_direction = Step.UP;
          this.chibiSignalStrength = (byte) (chibiSignalStrength + chibiSignalStrength_step);
        } else {
          this.chibiSignalStrength = (byte) (chibiSignalStrength - chibiSignalStrength_step);
        }
      }
    });
  }
        
  private void startRS485ErrorLogGenerator() {
    if (rS485ErrorLog_step == 0) {
      return;
    }
    vertx.setPeriodic(rS485ErrorLog_generator_period, id -> {
      if (rS485ErrorLog_direction == Step.UP) {
        if (rS485ErrorLog >= rS485ErrorLog_max) {
          rS485ErrorLog_direction = Step.DOWN;
          this.rS485ErrorLog = (short) (rS485ErrorLog - rS485ErrorLog_step);
        } else {
          this.rS485ErrorLog = (short) (rS485ErrorLog + rS485ErrorLog_step);
        }
      } else {
        if (rS485ErrorLog <= rS485ErrorLog_min) {
          rS485ErrorLog_direction = Step.UP;
          this.rS485ErrorLog = (short) (rS485ErrorLog + rS485ErrorLog_step);
        } else {
          this.rS485ErrorLog = (short) (rS485ErrorLog - rS485ErrorLog_step);
        }
      }
    });
  }
        
  private void startEthernetStatusGenerator() {
    if (ethernetStatus_step == 0) {
      return;
    }
    vertx.setPeriodic(ethernetStatus_generator_period, id -> {
      if (ethernetStatus_direction == Step.UP) {
        if (ethernetStatus >= ethernetStatus_max) {
          ethernetStatus_direction = Step.DOWN;
          this.ethernetStatus = (byte) (ethernetStatus - ethernetStatus_step);
        } else {
          this.ethernetStatus = (byte) (ethernetStatus + ethernetStatus_step);
        }
      } else {
        if (ethernetStatus <= ethernetStatus_min) {
          ethernetStatus_direction = Step.UP;
          this.ethernetStatus = (byte) (ethernetStatus + ethernetStatus_step);
        } else {
          this.ethernetStatus = (byte) (ethernetStatus - ethernetStatus_step);
        }
      }
    });
  }
        
  private void startChipTemperatureGenerator() {
    if (chipTemperature_step == 0) {
      return;
    }
    vertx.setPeriodic(chipTemperature_generator_period, id -> {
      if (chipTemperature_direction == Step.UP) {
        if (chipTemperature >= chipTemperature_max) {
          chipTemperature_direction = Step.DOWN;
          this.chipTemperature = (short) (chipTemperature - chipTemperature_step);
        } else {
          this.chipTemperature = (short) (chipTemperature + chipTemperature_step);
        }
      } else {
        if (chipTemperature <= chipTemperature_min) {
          chipTemperature_direction = Step.UP;
          this.chipTemperature = (short) (chipTemperature + chipTemperature_step);
        } else {
          this.chipTemperature = (short) (chipTemperature - chipTemperature_step);
        }
      }
    });
  }
        
  private void startStackCurrentGenerator() {
    if (stackCurrent_step == 0) {
      return;
    }
    vertx.setPeriodic(stackCurrent_generator_period, id -> {
      if (stackCurrent_direction == Step.UP) {
        if (stackCurrent >= stackCurrent_max) {
          stackCurrent_direction = Step.DOWN;
          this.stackCurrent = (short) (stackCurrent - stackCurrent_step);
        } else {
          this.stackCurrent = (short) (stackCurrent + stackCurrent_step);
        }
      } else {
        if (stackCurrent <= stackCurrent_min) {
          stackCurrent_direction = Step.UP;
          this.stackCurrent = (short) (stackCurrent + stackCurrent_step);
        } else {
          this.stackCurrent = (short) (stackCurrent - stackCurrent_step);
        }
      }
    });
  }
        
    private void stopStackCurrentCallback() {
        vertx.cancelTimer(stackCurrent_callback_id);
  }
        //fixme start_generator callback without sensor stackCurrent
//fixme start_generator callback without sensor usbVoltage
//fixme start_generator callback without sensor usbVoltageReached

    private void stopStackVoltageCallback() {
        vertx.cancelTimer(stackVoltage_callback_id);
  }
        //fixme start_generator callback without sensor stackVoltage
//fixme start_generator callback without sensor stackVoltageReached
//fixme start_generator callback without sensor stackCurrentReached

  private void startStackCurrentCallback() {
    logger.trace("stackCurrentCallbackPeriod is {}", stackCurrentCallbackPeriod);
    stackCurrent_callback_id = vertx.setPeriodic(stackCurrentCallbackPeriod, id -> {
      if (stackCurrent != stackCurrent_last_value_called_back) {
        stackCurrent_last_value_called_back = stackCurrent;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("stackCurrent sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getStackCurrent4Callback());
          }
        } else {
          logger.info("no handlerids found in stackCurrent callback");
        }
      } else {
        logger.debug("stackCurrent value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor stackCurrent
//fixme stop_generator callback without sensor usbVoltage
//fixme stop_generator callback without sensor usbVoltageReached

  private void startStackVoltageCallback() {
    logger.trace("stackVoltageCallbackPeriod is {}", stackVoltageCallbackPeriod);
    stackVoltage_callback_id = vertx.setPeriodic(stackVoltageCallbackPeriod, id -> {
      if (stackVoltage != stackVoltage_last_value_called_back) {
        stackVoltage_last_value_called_back = stackVoltage;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("stackVoltage sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getStackVoltage4Callback());
          }
        } else {
          logger.info("no handlerids found in stackVoltage callback");
        }
      } else {
        logger.debug("stackVoltage value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor stackVoltage
//fixme stop_generator callback without sensor stackVoltageReached
//fixme stop_generator callback without sensor stackCurrentReached

  private Buffer getStackCurrent4Callback() {
      byte options = (byte) 0;
      return getStackCurrentBuffer(CALLBACK_STACK_CURRENT, options);
  }
        //fixme getter callback without sensor stackCurrent
//fixme getter callback without sensor usbVoltage
//fixme getter callback without sensor usbVoltageReached

  private Buffer getStackVoltage4Callback() {
      byte options = (byte) 0;
      return getStackVoltageBuffer(CALLBACK_STACK_VOLTAGE, options);
  }
        //fixme getter callback without sensor stackVoltage
//fixme getter callback without sensor stackVoltageReached
//fixme getter callback without sensor stackCurrentReached

  private Buffer setStackCurrentCallbackPeriod(Packet packet) {
    stackCurrentCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (stackCurrentCallbackPeriod == 0) {
      stopStackCurrentCallback();
    } else {
      startStackCurrentCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_STACK_CURRENT_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer setStackVoltageCallbackPeriod(Packet packet) {
    stackVoltageCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (stackVoltageCallbackPeriod == 0) {
      stopStackVoltageCallback();
    } else {
      startStackVoltageCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_STACK_VOLTAGE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer setUSBVoltageCallbackPeriod(Packet packet) {
    uSBVoltageCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (uSBVoltageCallbackPeriod == 0) {
      stopUSBVoltageCallback();
    } else {
      startUSBVoltageCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_USB_VOLTAGE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getStackVoltageBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(stackVoltage));

    return buffer;
  }
        
  private Buffer getStackVoltageBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getStackVoltageBuffer(FUNCTION_GET_STACK_VOLTAGE, options);
  }

  private Buffer getStackVoltage(Packet packet) {
    logger.debug("function getStackVoltage");
    if (packet.getResponseExpected()) {
      return getStackVoltageBuffer(packet);
    }
    return null;
  }

  private Buffer getStackCurrentBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(stackCurrent));

    return buffer;
  }
        
  private Buffer getStackCurrentBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getStackCurrentBuffer(FUNCTION_GET_STACK_CURRENT, options);
  }

  private Buffer getStackCurrent(Packet packet) {
    logger.debug("function getStackCurrent");
    if (packet.getResponseExpected()) {
      return getStackCurrentBuffer(packet);
    }
    return null;
  }

  private Buffer getIsChibiPresentBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 1;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(isChibiPresent));

    return buffer;
  }
        
  private Buffer getIsChibiPresentBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getIsChibiPresentBuffer(FUNCTION_IS_CHIBI_PRESENT, options);
  }

  private Buffer getIsChibiPresent(Packet packet) {
    logger.debug("function getIsChibiPresent");
    if (packet.getResponseExpected()) {
      return getIsChibiPresentBuffer(packet);
    }
    return null;
  }

  private Buffer getChibiSignalStrengthBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 1;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(chibiSignalStrength));

    return buffer;
  }
        
  private Buffer getChibiSignalStrengthBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getChibiSignalStrengthBuffer(FUNCTION_GET_CHIBI_SIGNAL_STRENGTH, options);
  }

  private Buffer getChibiSignalStrength(Packet packet) {
    logger.debug("function getChibiSignalStrength");
    if (packet.getResponseExpected()) {
      return getChibiSignalStrengthBuffer(packet);
    }
    return null;
  }

  private Buffer getChibiErrorLogBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 8;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(chibiErrorLog));
    buffer.appendBytes(Utils.getUInt16(chibiErrorLog));
    buffer.appendBytes(Utils.getUInt16(chibiErrorLog));
    buffer.appendBytes(Utils.getUInt16(chibiErrorLog));

    return buffer;
  }
        
  private Buffer getChibiErrorLogBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getChibiErrorLogBuffer(FUNCTION_GET_CHIBI_ERROR_LOG, options);
  }

  private Buffer getChibiErrorLog(Packet packet) {
    logger.debug("function getChibiErrorLog");
    if (packet.getResponseExpected()) {
      return getChibiErrorLogBuffer(packet);
    }
    return null;
  }

  private Buffer getRS485ErrorLogBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(rS485ErrorLog));

    return buffer;
  }
        
  private Buffer getRS485ErrorLogBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getRS485ErrorLogBuffer(FUNCTION_GET_RS485_ERROR_LOG, options);
  }

  private Buffer getRS485ErrorLog(Packet packet) {
    logger.debug("function getRS485ErrorLog");
    if (packet.getResponseExpected()) {
      return getRS485ErrorLogBuffer(packet);
    }
    return null;
  }

  private Buffer getWifiStatusBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 36;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(wifiStatus));
    buffer.appendBytes(Utils.getUInt8A(wifiStatus));
    buffer.appendBytes(Utils.getUInt8A(wifiStatus));
    buffer.appendBytes(Utils.getUInt16(wifiStatus));
    buffer.appendBytes(Utils.getUInt8A(wifiStatus));
    buffer.appendBytes(Utils.getUInt8A(wifiStatus));
    buffer.appendBytes(Utils.getUInt8A(wifiStatus));
    buffer.appendBytes(Utils.getUInt32(wifiStatus));
    buffer.appendBytes(Utils.getUInt32(wifiStatus));
    buffer.appendBytes(Utils.getUInt8A(wifiStatus));

    return buffer;
  }
        
  private Buffer getWifiStatusBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getWifiStatusBuffer(FUNCTION_GET_WIFI_STATUS, options);
  }

  private Buffer getWifiStatus(Packet packet) {
    logger.debug("function getWifiStatus");
    if (packet.getResponseExpected()) {
      return getWifiStatusBuffer(packet);
    }
    return null;
  }

  private Buffer getWifiBufferInfoBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 8;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(wifiBufferInfo));
    buffer.appendBytes(Utils.getUInt16(wifiBufferInfo));
    buffer.appendBytes(Utils.getUInt16(wifiBufferInfo));

    return buffer;
  }
        
  private Buffer getWifiBufferInfoBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getWifiBufferInfoBuffer(FUNCTION_GET_WIFI_BUFFER_INFO, options);
  }

  private Buffer getWifiBufferInfo(Packet packet) {
    logger.debug("function getWifiBufferInfo");
    if (packet.getResponseExpected()) {
      return getWifiBufferInfoBuffer(packet);
    }
    return null;
  }

  private Buffer getUSBVoltageBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(uSBVoltage));

    return buffer;
  }
        
  private Buffer getUSBVoltageBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getUSBVoltageBuffer(FUNCTION_GET_USB_VOLTAGE, options);
  }

  private Buffer getUSBVoltage(Packet packet) {
    logger.debug("function getUSBVoltage");
    if (packet.getResponseExpected()) {
      return getUSBVoltageBuffer(packet);
    }
    return null;
  }

  private Buffer getIsEthernetPresentBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 1;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(isEthernetPresent));

    return buffer;
  }
        
  private Buffer getIsEthernetPresentBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getIsEthernetPresentBuffer(FUNCTION_IS_ETHERNET_PRESENT, options);
  }

  private Buffer getIsEthernetPresent(Packet packet) {
    logger.debug("function getIsEthernetPresent");
    if (packet.getResponseExpected()) {
      return getIsEthernetPresentBuffer(packet);
    }
    return null;
  }

  private Buffer getEthernetStatusBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 58;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(ethernetStatus));
    buffer.appendBytes(Utils.getUInt8A(ethernetStatus));
    buffer.appendBytes(Utils.getUInt8A(ethernetStatus));
    buffer.appendBytes(Utils.getUInt8A(ethernetStatus));
    buffer.appendBytes(Utils.getUInt32(ethernetStatus));
    buffer.appendBytes(Utils.getUInt32(ethernetStatus));
    buffer.appendBytes(Utils.stringBuffer(ethernetStatus));

    return buffer;
  }
        
  private Buffer getEthernetStatusBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getEthernetStatusBuffer(FUNCTION_GET_ETHERNET_STATUS, options);
  }

  private Buffer getEthernetStatus(Packet packet) {
    logger.debug("function getEthernetStatus");
    if (packet.getResponseExpected()) {
      return getEthernetStatusBuffer(packet);
    }
    return null;
  }

  private Buffer getChipTemperatureBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(chipTemperature));

    return buffer;
  }
        
  private Buffer getChipTemperatureBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getChipTemperatureBuffer(FUNCTION_GET_CHIP_TEMPERATURE, options);
  }

  private Buffer getChipTemperature(Packet packet) {
    logger.debug("function getChipTemperature");
    if (packet.getResponseExpected()) {
      return getChipTemperatureBuffer(packet);
    }
    return null;
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
  private Buffer getStackVoltageCallbackPeriod(Packet packet) {
    logger.debug("function getStackVoltageCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_STACK_VOLTAGE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(stackVoltageCallbackPeriod));

      return buffer;
    }

    return null;
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
    buffer.appendBytes(Utils.getUInt32(stackCurrentCallbackPeriod));

      return buffer;
    }

    return null;
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
    buffer.appendBytes(Utils.getUInt32(uSBVoltageCallbackPeriod));

      return buffer;
    }

    return null;
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
