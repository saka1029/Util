package test.util.srt;

import static org.junit.Assert.*;

import util.srt.Srt;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

import org.junit.Test;

public class TestSrt {

    @Test
    public void testMatcher() {
        Matcher m = Srt.NO_PAT.matcher("1");
        assertTrue(m.matches());
        assertEquals("1", m.group());
    }

    @Test
    public void testMadMaxFuryRoad() throws IOException {
        String encoding = "Shift_JIS";
        File dir = new File("D:/BitComet/Mad.Max.Fury.Road.2015.1080p.WEB-DL.DD5.1.H264-RARBG");
        File in = new File(dir, "Mad.Max.Fury.Road.2015.TELESYNC.XViD.MrSeeN-SiMPLE.srt");
        File out = new File(dir, "Mad.Max.Fury.Road.2015.1080p.WEB-DL.DD5.1.H264-RARBG.srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:00:21,040", "01:50:49,599", "00:00:18,686", "01:51:02,657");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }

    @Test
    public void testLastKnights() throws IOException {
        String encoding = "Shift_JIS";
        File dir = new File("D:/BitComet/Last.Knights.2015.1080p.BluRay.AC3.x264-ETRG");
        File in = new File(dir, "Last.Knights.2015.720p.WEB-DL.DD5.1.H264-RARBG.srt");
        File out = new File(dir, "Last.Knights.2015.1080p.BluRay.AC3.x264-ETRG..srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:01:16,242", "01:46:38,626", "00:00:57,216", "01:42:01,614");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }

    @Test
    public void testTransformers() throws IOException {
        String encoding = "utf-8";
        File dir = new File("D:/BitComet/Transformers 2 - Revenge of The Fallen (2009)[1080p]");
        File in = new File(dir, "Transformers Revenge Of The Fallen 2009 3Li BluRay Japanese.srt");
        File out = new File(dir, "Transformers.Revenge.Of.The.Fallen.2009.IMAX.1080p.BrRip.x264.YIFY.srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:00:57,200", "02:22:02,390", "00:00:57,099", "02:22:34,087");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }


    @Test
    public void testElle() throws IOException {
        String encoding = "utf-8";
        File dir = new File("D:\\BitComet\\Elle (2016) [1080p]");
        File in = new File(dir, "Elle.2016.1080p.BluRay.x264-[YTS.PE] - コピー.srt");
        File out = new File(dir, "Elle.2016.1080p.BluRay.x264-[YTS.PE].srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:03:28,333", "02:02:06,000", "00:03:37,333", "02:02:22,000");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }

    @Test
    public void testDivergent() throws IOException {
        String encoding = "utf-8";
        File dir = new File("D:/BitComet/Divergente - Insurgente (2015)[720p]");
        File in = new File(dir, "Insurgent (2015).srt");
        File out = new File(dir, "Divergente - Insurgente (2015) 720p.srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.add("-00:00:19,857");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }

    @Test
    public void testRenegades() throws IOException {
        String encoding = "utf-8";
        File dir = new File("D:\\BitComet\\Renegades 2017 BluRay 1080p AC3 x264-3Li");
        File in = new File(dir, "Renegades 2017 BluRay 1080p AC3 x264-3Li.origin.srt");
        File out = new File(dir, "Renegades 2017 BluRay 1080p AC3 x264-3Li.srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:01:06,816", "01:35:50,161", "00:01:20,418", "01:35:58,267");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }

    @Test
    public void testKarlMarx() throws IOException {
        String encoding = "utf-8";
        File dir = new File("D:\\BitComet\\the-young-karl-marx-2017");
        File in = new File(dir, "jp.srt");
        File out = new File(dir, "The.Young.Karl.Marx.2017.1080p.BluRay.x264-[YTS.AM].srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:02:42,000", "01:49:00,000", "00:03:02,000", "01:49:14,000");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }

    @Test
    public void testEqualizer2() throws IOException {
        String encoding = "utf-8";
        File dir = new File("d:\\BitComet\\The Equalizer 2 (2018).720p.H264.italian.english.Ac3-5.1.sub.ita-MIRCrew");
        File in = new File(dir, "download.srt");
        File out = new File(dir, "The Equalizer 2 (2018).720p.h264.ita.eng.sub.ita-MIRCrew.srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:02:06,505", "01:53:42,593", "00:02:35,000", "01:54:40,000");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }

    @Test
    public void testAlita() throws IOException {
        String encoding = "utf-8";
        File dir = new File("D:\\BitComet\\Alita.Battle.Angel.2019.1080p.HQTS.GOLD-EDITION.x264-CHS[TGx]");
        File in = new File(dir, "change.srt");
        File out = new File(dir, "Alita.Battle.Angel.2019.1080p.HQTS.GOLD-EDITION.x264-CHS.srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:00:31,000", "01:56:31,000", "00:00:00,000", "01:56:00,000");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }


    @Test
    public void testTombRaider() throws IOException {
        String encoding = "utf-8";
        File dir = new File("D:\\BitComet\\Tomb Raider 2018");
        File in = new File(dir, "Tomb.Raider.2018.1080p.BluRay.REMUX.AVC.DTS-HD.MA.TrueHD.7.1.Atmos-FGT.srt");
        File out = new File(dir, "Tomb Raider 2018 BDRip ITA ENG 1080p x265 Paso77.srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:00:00,000", "01:57:00,000", "00:00:01,000", "01:57:01,000");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }

    @Test
    public void testStarWarsEp3() throws IOException {
        String encoding = "utf-8";
        File dir = new File("D:\\BitComet\\Star Wars The Complete Saga 1080p BluRay AC3 x264-tomcat12[ETRG]\\Star.Wars.Episode.III.Revenge.of.the.Sith.2005");
        File in = new File(dir, "Star.Wars.Episode.III.Revenge.of.the.Sith.2005.jp.org.srt");
        File out = new File(dir, "Star.Wars.Episode.III.Revenge.of.the.Sith.2005.srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:00:00,000", "02:10:00,000", "00:00:02,000", "02:10:02,000");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }

    @Test
    public void testJoker2019() throws IOException {
        String encoding = "utf-8";
        File dir = new File("D:\\BitComet\\Joker 2019 1080p HC HDRip x264 AAC - LOKiHD - Telly");
        File in = new File(dir, "Joker 2019.srt");
        File out = new File(dir, "Joker 2019 1080p HC HDRip x264 AAC - LOKiHD - Telly.srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:02:39,179", "01:51:48,675", "00:03:32,000", "01:53:37,000");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }

    @Test
    public void testFastAndFurious2019() throws IOException {
        String encoding = "utf-8";
        File dir = new File("D:\\BitComet\\Fast & Furious Presents Hobbs & Shaw (2019)");
        File in = new File(dir, "japanese.srt");
        File out = new File(dir, "Fast & Furious Presents Hobbs & Shaw (2019) AC3 5.1 ITA.ENG 1080p H265 sub ita.eng Sp33dy94-MIRCrew.srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:00:40,000", "02:00:44,000", "00:00:42,000", "02:00:46,000");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }

    @Test
    public void testParasite2019() throws IOException {
        String encoding = "utf-8";
        File dir = new File("D:\\BitComet\\Parasite (Gisaengchung) (2019) [BD 1080p HEVC VOSTA+VOSTFR] HR-GZ+DR");
        File in = new File(dir, "Parasite.2019.1080p.HDRip.X264.AC3-EVO jp.srt");
        File out = new File(dir, "[Hakata Ramen] Parasite (2019) (Korean) [BD 1080p x265].srt");
        Srt srt = new Srt();
        srt.read(in, encoding);
        srt.scale("00:01:49,469", "02:07:07,276", "00:01:39,456", "02:06:57,216");
        System.out.println(srt);
        srt.write(out, encoding, true);
    }


}
