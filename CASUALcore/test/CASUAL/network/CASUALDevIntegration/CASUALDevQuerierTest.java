/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CASUAL.network.CASUALDevIntegration;


import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

    
    
    
    
/**
 *
 * @author adam
 */
public class CASUALDevQuerierTest {
    
    public CASUALDevQuerierTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    @SuppressWarnings({"rawtypes"})
    public void testDoPropertySearch() throws Exception {
        System.out.println("getData");
        CASUALDevQuerier instance = new CASUALDevQuerier(BUILDPROP, new String[]{"CASUAL"});
        String expResult = "http://builds.casual-dev.com/files/all/EasyGlassInstaller.zip";
        String[] result=new String[]{};
        //for (int i=0; i<30; i++){
            result=instance.recursiveFolderSearch();
        //}
        
        for (String name:result){
            System.out.println(name);
        }
        System.out.println("Result returned "+result.length + " packages available");
        assert(Arrays.asList(result).contains(expResult));

    }
    @Test
    public void testGetPackages(){
        System.out.println("getPackages");
        CASUALDevQuerier instance = new CASUALDevQuerier(BUILDPROP, new String[]{"CASUAL"});
        String expResult = "http://builds.casual-dev.com/files/all/EasyGlassInstaller.zip";
        CASUALPackage[] packs=instance.getPackages();
        for (CASUALPackage pack:packs){
            System.out.println(pack.isValid());
            assert (pack.isValid());
        }
    }

    
//build prop from oppo x909    
final static String BUILDPROP="\"# begin build properties\n" +
"# autogenerated by buildinfo.sh\n" +
"ro.build.version.ota=X909EROM_12.2.09_GLO_009_130814\n" +
"ro.build.version.type=\n" +
"ro.build.soft.version=2.09\n" +
"ro.buiid.soft.snapshot=\n" +
"ro.build.date.Ymd=20130814\n" +
"ro.build.date.ymd=130814\n" +
"ro.build.date.YmdHM=201308142207\n" +
"ro.oppo.product.model=X909\n" +
"ro.oppo.product.brand=OPPO\n" +
"ro.build.kernel.id=3.4.0-8229\n" +
"ro.build.soft.majorversion=1\n" +
"ro.build.id=JRO03L\n" +
"ro.build.version.incremental=eng.root.20130814.220707\n" +
"ro.build.version.sdk=16\n" +
"ro.build.version.codename=REL\n" +
"ro.build.version.release=4.1.1\n" +
"ro.build.date=Wed Aug 14 22:07:52 EDT 2013\n" +
"ro.build.date.utc=1376532472\n" +
"ro.build.type=user\n" +
"ro.build.user=root\n" +
"ro.build.host=ubuntu\n" +
"ro.build.tags=release-keys\n" +
"ro.product.model=X909\n" +
"ro.product.brand=OPPO\n" +
"ro.product.name=oppo_12069\n" +
"ro.product.device=FIND5\n" +
"ro.product.board=MSM8960\n" +
"ro.product.cpu.abi=armeabi-v7a\n" +
"ro.product.cpu.abi2=armeabi\n" +
"ro.product.manufacturer=OPPO\n" +
"ro.product.locale.language=en\n" +
"ro.product.locale.region=US\n" +
"ro.wifi.channels=\n" +
"ro.board.platform=msm8960\n" +
"# ro.build.product is obsolete; use ro.product.device\n" +
"ro.build.product=FIND5\n" +
"# Do not try to parse ro.build.description or .fingerprint\n" +
"ro.build.description=msm8960-user 4.1.1 JRO03L eng.root.20130814.220707 release-keys\n" +
"ro.build.fingerprint=OPPO/oppo_12069/FIND5:4.1.1/JRO03L/1357823013:user/release-keys\n" +
"ro.build.characteristics=default\n" +
"persist.sys.timezone=Asia/Shanghai\n" +
"ro.build.version.opporom=OPPOEROM_V1.0.0_3333L\n" +
"ro.oppo.version=US\n" +
"persist.sys.timezone=America/New_York\n" +
"persist.sys.oppo.region=US\n" +
"# end build properties\n" +
"#\n" +
"# system.prop for surf\n" +
"#\n" +
"\n" +
"rild.libpath=/system/lib/libril-qc-qmi-1.so\n" +
"rild.libargs=-d /dev/smd0\n" +
"persist.rild.nitz_plmn=\n" +
"persist.rild.nitz_long_ons_0=\n" +
"persist.rild.nitz_long_ons_1=\n" +
"persist.rild.nitz_long_ons_2=\n" +
"persist.rild.nitz_long_ons_3=\n" +
"persist.rild.nitz_short_ons_0=\n" +
"persist.rild.nitz_short_ons_1=\n" +
"persist.rild.nitz_short_ons_2=\n" +
"persist.rild.nitz_short_ons_3=\n" +
"ril.subscription.types=NV,RUIM\n" +
"DEVICE_PROVISIONED=1\n" +
"debug.sf.hw=1\n" +
"debug.egl.hw=1\n" +
"debug.composition.type=dyn\n" +
"dalvik.vm.heapsize=36m\n" +
"debug.enable.wl_log=1\n" +
"#ifndef VEDOR_EDIT\n" +
"#peirs@OnLineRD.framework, 2012.11.08, modify to solve qulcomm problem:\n" +
"#Wallpaper will flash when quit app to desktop at landscape mode.\n" +
"#The qualcomm's JB version has problem in bypass feature, ant this problem can not resolve\n" +
"#at a short time, so close it at this product.\n" +
"# debug.mdpcomp.maxlayer=3\n" +
"#else /* VEDOR_EDIT */\n" +
"  debug.mdpcomp.maxlayer=2\n" +
"#endif /* VEDOR_EDIT */\n" +
"debug.mdpcomp.logs=0\n" +
"#ifdef VENDOR_EDIT\n" +
"#sunguojun@@Plf.GraphicTech, 2013-06-08, modify for GTS encryption vedio play\n" +
"persist.gralloc.cp.level3=1\n" +
"#endif /* VEDOR_EDIT */\n" +
"\n" +
"#\n" +
"# system props for the cne module\n" +
"#\n" +
"persist.cne.bat.range.low.med=30\n" +
"persist.cne.bat.range.med.high=60\n" +
"persist.cne.loc.policy.op=/system/etc/OperatorPolicy.xml\n" +
"persist.cne.loc.policy.user=/system/etc/UserPolicy.xml\n" +
"persist.cne.bwbased.rat.sel=false\n" +
"persist.cne.snsr.based.rat.mgt=false\n" +
"persist.cne.bat.based.rat.mgt=false\n" +
"persist.cne.rat.acq.time.out=30000\n" +
"persist.cne.rat.acq.retry.tout=0\n" +
"persist.cne.feature=1\n" +
"\n" +
"ro.hdmi.enable=true\n" +
"\n" +
"#ifndef VENDOR_EDIT\n" +
"#TuGuang@OnLineRD.MultiMediaService.AF, 2012/11/27, Modify for to not use lpa\n" +
"#lpa.decode=true\n" +
"#else /* VENDOR_EDIT */\n" +
"lpa.decode=false  \n" +
"#endif /* VENDOR_EDIT */\n" +
"\n" +
"lpa.use-stagefright=true\n" +
"\n" +
"#system props for the MM modules\n" +
"\n" +
"media.stagefright.enable-player=true\n" +
"media.stagefright.enable-http=true\n" +
"media.stagefright.enable-aac=true\n" +
"media.stagefright.enable-qcp=true\n" +
"media.stagefright.enable-fma2dp=true\n" +
"media.stagefright.enable-scan=true\n" +
"mmp.enable.3g2=true\n" +
"\n" +
"#\n" +
"# system props for the data modules\n" +
"#\n" +
"ro.use_data_netmgrd=true\n" +
"\n" +
"#system props for time-services\n" +
"persist.timed.enable=true\n" +
"\n" +
"# System props for audio\n" +
"persist.audio.fluence.mode=endfire\n" +
"persist.audio.vr.enable=false\n" +
"persist.audio.handset.mic=digital\n" +
"\n" +
"# System prop to select audio resampler quality\n" +
"af.resampler.quality=255\n" +
"# System prop to select MPQAudioPlayer by default on mpq8064\n" +
"mpq.audio.decode=true\n" +
"\n" +
"#\n" +
"# system prop for opengles version\n" +
"#\n" +
"# 131072 is decimal for 0x20000 to report version 2\n" +
"ro.opengles.version=131072\n" +
"\n" +
"#\n" +
"# system property for Bluetooth Handsfree Profile version\n" +
"#\n" +
"ro.bluetooth.hfp.ver=1.6\n" +
"#\n" +
"#system prop for Bluetooth hci transport\n" +
"ro.qualcomm.bt.hci_transport=smd\n" +
"#\n" +
"# system prop for requesting Master role in incoming Bluetooth connection.\n" +
"#\n" +
"ro.bluetooth.request.master=true\n" +
"#\n" +
"# system prop for Bluetooth Auto connect for remote initated connections\n" +
"#\n" +
"ro.bluetooth.remote.autoconnect=true\n" +
"# system property for Bluetooth discoverability time out in seconds\n" +
"# 0: Always discoverable\n" +
"#debug.bt.discoverable_time=0\n" +
"\n" +
"#liuhd add for nfc\n" +
"ro.nfc.port=I2C\n" +
"\n" +
"#system prop for switching gps driver to qmi\n" +
"persist.gps.qmienabled=true\n" +
"\n" +
"# System property for cabl\n" +
"ro.qualcomm.cabl=1\n" +
"\n" +
"# System props for telephony\n" +
"# System prop to turn on CdmaLTEPhone always\n" +
"telephony.lteOnCdmaDevice=1\n" +
"#\n" +
"# System prop for sending transmit power request to RIL during WiFi hotspot on/off\n" +
"#\n" +
"ro.ril.transmitpower=true\n" +
"\n" +
"#\n" +
"#Simulate sdcard on /data/media\n" +
"#\n" +
"#ifdef VENDOR_EDIT\n" +
"#Eric.Song@OnLineRD.Driver, add for disable the sdcard service\n" +
"persist.fuse_sdcard=false\n" +
"ro.hwui.text_cache_width=2048\n" +
"\n" +
"#\n" +
"# Supports warmboot capabilities\n" +
"#\n" +
"ro.warmboot.capability=1\n" +
"\n" +
"#snapdragon value add features\n" +
"ro.qc.sdk.audio.ssr=false\n" +
"##fluencetype can be \"fluence\" or \"fluencepro\" or \"none\"\n" +
"#2012-11-20 zhzhyon Modify for flunece type\n" +
"#ro.qc.sdk.audio.fluencetype=none\n" +
"ro.qc.sdk.audio.fluencetype=fluence\n" +
"#2012-11-20 zhzhyon Modify end\n" +
"ro.qc.sdk.camera.facialproc=false\n" +
"ro.qc.sdk.sensors.gestures=false\n" +
"\n" +
"# mwalker@OnLineRD.SDKAPP, add for generate binder debug information\n" +
"ro.assert.binder.enable=true\n" +
"persist.sys.assert.panic=false\n" +
"persist.sys.assert.enable=false\n" +
"\n" +
"# mwalker@OnLineRD.SDK, for oppo assiant\n" +
"ro.build.version.opposdk=1.6\n" +
"\n" +
"# mwalker@OnLineRD.SDK.theme\n" +
"ro.theme.version=4\n" +
"\n" +
"#ifdef VENDOR_EDIT\n" +
"#licx@OnLineRD.framework, add for fix HDMI orientation\n" +
"hdmi.Landscape=0\n" +
"#endif /* VENDOR_EDIT */\n" +
"\n" +
"#ifdef VENDOR_EDIT\n" +
"#feiser@OnLineRD.DeviceService, 2012/10/31\n" +
"ro.branch=X909_MC\n" +
"ro.xxversion=V1.0\n" +
"ro.bootversion=V1.1\n" +
"persist.oppo.opporom=true\n" +
"#endif\n" +
"#ifdef VENDOR_EDIT\n" +
"#sunjianbo camera\n" +
"persist.camera.zsl.interval=0\n" +
"persist.camera.zsl.queuedepth=3\n" +
"persist.camera.zsl.backlookcnt=3\n" +
"#persist.camera.full.liveshot=0\n" +
"#endif /* VENDOR_EDIT */\n" +
"#ifdef VENDOR_EDIT\n" +
"#yansen usb\n" +
"#persist.service.adb.enable=1\n" +
"#endif /* VENDOR_EDIT */\n" +
"#Jiangsm add for filesyst\n" +
"sys.fs.type=fat32\n" +
"#endif /* VENDOR_EDIT*/\n" +
"\n" +
"#ifdef VENDOR_EDIT\n" +
"#baoqibiao@GraphicTech.Graphics,add for increase texture_cahce\n" +
"ro.hwui.texture_cache_size=48\n" +
"#endif /* VENDOR_EDIT */\n" +
"\n" +
"#\n" +
"# ADDITIONAL_BUILD_PROPERTIES\n" +
"#\n" +
"keyguard.no_require_sim=true\n" +
"ro.com.android.dataroaming=false\n" +
"ro.com.android.dateformat=MM-dd-yyyy\n" +
"ro.config.ringtone=ringtone_001.ogg\n" +
"ro.config.notification_sound=notification_001.ogg\n" +
"ro.carrier=unknown\n" +
"ro.config.alarm_alert=alarm_005.ogg\n" +
"ro.vendor.extension_library=/system/lib/libqc-opt.so\n" +
"ro.setupwizard.mode=OPTIONAL\n" +
"ro.com.google.gmsversion=4.1_r6\n" +
"ro.com.google.clientidbase=android-oppo\n" +
"dalvik.vm.heapstartsize=8m\n" +
"dalvik.vm.heapgrowthlimit=192m\n" +
"dalvik.vm.heapsize=256m\n" +
"dalvik.vm.heaputilization=0.25\n" +
"dalvik.vm.heapidealfree=8388608\n" +
"dalvik.vm.heapconcurrentstart=2097152\n" +
"drm.service.enabled=true\n" +
"net.bt.name=Android\n" +
"dalvik.vm.stack-trace-file=/data/anr/traces.txt\n" +
"\"";


}
