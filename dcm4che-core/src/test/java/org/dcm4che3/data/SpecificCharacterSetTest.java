/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is part of dcm4che, an implementation of DICOM(TM) in
 * Java(TM), hosted at https://github.com/dcm4che.
 *
 * The Initial Developer of the Original Code is
 * Agfa Healthcare.
 * Portions created by the Initial Developer are Copyright (C) 2011
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * See @authors listed below
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package org.dcm4che3.data;


import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Gunter Zeilinger <gunterze@gmail.com>
 */
public class SpecificCharacterSetTest {

    private static final String LT_DELIMS = "\t\n\f\r";
    private static final String PN_DELIMS = "^=\\";
    private static final String GERMAN_PERSON_NAME = "Äneas^Rüdiger";
    private static final String FRENCH_PERSON_NAME = "Buc^Jérôme";
    private static final String RUSSIAN_PERSON_NAME = "Люкceмбypг";
    private static final String ARABIC_PERSON_NAME = "قباني^لنزار";
    private static final String GREEK_PERSON_NAME = "Διονυσιος";
    private static final String HEBREW_PERSON_NAME = "שרון^דבורה";
    private static final String JAPANESE_PERSON_NAME_ASCII =
            "Yamada^Tarou=山田^太郎=やまだ^たろう";
    private static final String JAPANESE_PERSON_NAME_JISX0201 =
            "ﾔﾏﾀﾞ^ﾀﾛｳ=山田^太郎=やまだ^たろう";
    private static final String KOREAN_PERSON_NAME =
            "Hong^Gildong=洪^吉洞=홍^길동";
    private static final String KOREAN_LONG_TEXT = 
            "The 1st line includes 길동.\r\n" +
            "The 2nd line includes 길동, too.\r\n" +
            "The 3rd line.";
    private static final String CHINESE_PERSON_NAME_GB2312 =
            "Zhang^XiaoDong=张^小东=";
    private static final String CHINESE_LONG_TEXT_GB2312 =
            "1.第一行文字。\r\n" +
            "2.第二行文字。\r\n" +
            "3.第三行文字。\r\n";
    private static final String CHINESE_PERSON_NAME_UTF8 =
            "Wang^XiaoDong=王^小東=";
    private static final String CHINESE_PERSON_NAME_GB18030 =
            "Wang^XiaoDong=王^小东=";
    private static final String FINNISH_PERSON_NAME = "Šiškino^Žuža";

    private static final byte[] GERMAN_PERSON_NAME_BYTE = {
            (byte) 0xc4, (byte) 0x6e, (byte) 0x65, (byte) 0x61, (byte) 0x73,
            (byte) 0x5e, (byte) 0x52, (byte) 0xfc, (byte) 0x64, (byte) 0x69,
            (byte) 0x67, (byte) 0x65, (byte) 0x72 };

    private static final byte[] FRENCH_PERSON_NAME_BYTE = {
            (byte) 0x42, (byte) 0x75, (byte) 0x63, (byte) 0x5e, (byte) 0x4a,
            (byte) 0xe9, (byte) 0x72, (byte) 0xf4, (byte) 0x6d, (byte) 0x65 };

    private static final byte[] RUSSIAN_PERSON_NAME_BYTE = {
            (byte) 0xbb, (byte) 0xee, (byte) 0xda, (byte) 0x63, (byte) 0x65,
            (byte) 0xdc, (byte) 0xd1, (byte) 0x79, (byte) 0x70, (byte) 0xd3 };

    private static final byte[] ARABIC_PERSON_NAME_BYTE = {
            (byte) 0xe2, (byte) 0xc8, (byte) 0xc7, (byte) 0xe6, (byte) 0xea,
            (byte) 0x5e, (byte) 0xe4, (byte) 0xe6, (byte) 0xd2, (byte) 0xc7,
            (byte) 0xd1 };

    private static final byte[] GREEK_PERSON_NAME_BYTE = {
            (byte) 0xc4, (byte) 0xe9, (byte) 0xef, (byte) 0xed, (byte) 0xf5,
            (byte) 0xf3, (byte) 0xe9, (byte) 0xef, (byte) 0xf2 };

    private static final byte[] HEBREW_PERSON_NAME_BYTE = {
            (byte) 0xf9, (byte) 0xf8, (byte) 0xe5, (byte) 0xef, (byte) 0x5e,
            (byte) 0xe3, (byte) 0xe1, (byte) 0xe5, (byte) 0xf8, (byte) 0xe4 };

    private static final byte[] JAPANESE_PERSON_NAME_ASCII_BYTES = {
            (byte) 0x59, (byte) 0x61, (byte) 0x6d, (byte) 0x61, (byte) 0x64,
            (byte) 0x61, (byte) 0x5e, (byte) 0x54, (byte) 0x61, (byte) 0x72,
            (byte) 0x6f, (byte) 0x75, (byte) 0x3d, (byte) 0x1b, (byte) 0x24,
            (byte) 0x42, (byte) 0x3b, (byte) 0x33, (byte) 0x45, (byte) 0x44,
            (byte) 0x1b, (byte) 0x28, (byte) 0x42, (byte) 0x5e, (byte) 0x1b,
            (byte) 0x24, (byte) 0x42, (byte) 0x42, (byte) 0x40, (byte) 0x4f,
            (byte) 0x3a, (byte) 0x1b, (byte) 0x28, (byte) 0x42, (byte) 0x3d,
            (byte) 0x1b, (byte) 0x24, (byte) 0x42, (byte) 0x24, (byte) 0x64,
            (byte) 0x24, (byte) 0x5e, (byte) 0x24, (byte) 0x40, (byte) 0x1b,
            (byte) 0x28, (byte) 0x42, (byte) 0x5e, (byte) 0x1b, (byte) 0x24,
            (byte) 0x42, (byte) 0x24, (byte) 0x3f, (byte) 0x24, (byte) 0x6d,
            (byte) 0x24, (byte) 0x26, (byte) 0x1b, (byte) 0x28, (byte) 0x42 };

    private static final byte[] JAPANESE_PERSON_NAME_JISX0201_BYTES = {
            (byte) 0xd4, (byte) 0xcf, (byte) 0xc0, (byte) 0xde, (byte) 0x5e,
            (byte) 0xc0, (byte) 0xdb, (byte) 0xb3, (byte) 0x3d, (byte) 0x1b,
            (byte) 0x24, (byte) 0x42, (byte) 0x3b, (byte) 0x33, (byte) 0x45,
            (byte) 0x44, (byte) 0x1b, (byte) 0x28, (byte) 0x4a, (byte) 0x5e,
            (byte) 0x1b, (byte) 0x24, (byte) 0x42, (byte) 0x42, (byte) 0x40,
            (byte) 0x4f, (byte) 0x3a, (byte) 0x1b, (byte) 0x28, (byte) 0x4a,
            (byte) 0x3d, (byte) 0x1b, (byte) 0x24, (byte) 0x42, (byte) 0x24,
            (byte) 0x64, (byte) 0x24, (byte) 0x5e, (byte) 0x24, (byte) 0x40,
            (byte) 0x1b, (byte) 0x28, (byte) 0x4a, (byte) 0x5e, (byte) 0x1b,
            (byte) 0x24, (byte) 0x42, (byte) 0x24, (byte) 0x3f, (byte) 0x24,
            (byte) 0x6d, (byte) 0x24, (byte) 0x26, (byte) 0x1b, (byte) 0x28,
            (byte) 0x4a };

    private static final byte[] KOREAN_PERSON_NAME_BYTES = {
            (byte) 0x48, (byte) 0x6f, (byte) 0x6e, (byte) 0x67, (byte) 0x5e,
            (byte) 0x47, (byte) 0x69, (byte) 0x6c, (byte) 0x64, (byte) 0x6f,
            (byte) 0x6e, (byte) 0x67, (byte) 0x3d, (byte) 0x1b, (byte) 0x24,
            (byte) 0x29, (byte) 0x43, (byte) 0xfb, (byte) 0xf3, (byte) 0x5e,
            (byte) 0x1b, (byte) 0x24, (byte) 0x29, (byte) 0x43, (byte) 0xd1,
            (byte) 0xce, (byte) 0xd4, (byte) 0xd7, (byte) 0x3d, (byte) 0x1b,
            (byte) 0x24, (byte) 0x29, (byte) 0x43, (byte) 0xc8, (byte) 0xab,
            (byte) 0x5e, (byte) 0x1b, (byte) 0x24, (byte) 0x29, (byte) 0x43,
            (byte) 0xb1, (byte) 0xe6, (byte) 0xb5, (byte) 0xbf };

    private static final byte[] KOREAN_LONG_TEXT_BYTES = {
            (byte) 0x54, (byte) 0x68, (byte) 0x65, (byte) 0x20, (byte) 0x31,
            (byte) 0x73, (byte) 0x74, (byte) 0x20, (byte) 0x6c, (byte) 0x69,
            (byte) 0x6e, (byte) 0x65, (byte) 0x20, (byte) 0x69, (byte) 0x6e,
            (byte) 0x63, (byte) 0x6c, (byte) 0x75, (byte) 0x64, (byte) 0x65,
            (byte) 0x73, (byte) 0x20, (byte) 0x1b, (byte) 0x24, (byte) 0x29,
            (byte) 0x43, (byte) 0xb1, (byte) 0xe6, (byte) 0xb5, (byte) 0xbf,
            (byte) 0x2e, (byte) 0x0d, (byte) 0x0a, (byte) 0x54, (byte) 0x68,
            (byte) 0x65, (byte) 0x20, (byte) 0x32, (byte) 0x6e, (byte) 0x64,
            (byte) 0x20, (byte) 0x6c, (byte) 0x69, (byte) 0x6e, (byte) 0x65,
            (byte) 0x20, (byte) 0x69, (byte) 0x6e, (byte) 0x63, (byte) 0x6c,
            (byte) 0x75, (byte) 0x64, (byte) 0x65, (byte) 0x73, (byte) 0x20,
            (byte) 0x1b, (byte) 0x24, (byte) 0x29, (byte) 0x43, (byte) 0xb1,
            (byte) 0xe6, (byte) 0xb5, (byte) 0xbf, (byte) 0x2c, (byte) 0x20,
            (byte) 0x74, (byte) 0x6f, (byte) 0x6f, (byte) 0x2e, (byte) 0x0d,
            (byte) 0x0a, (byte) 0x54, (byte) 0x68, (byte) 0x65, (byte) 0x20,
            (byte) 0x33, (byte) 0x72, (byte) 0x64, (byte) 0x20, (byte) 0x6c,
            (byte) 0x69, (byte) 0x6e, (byte) 0x65, (byte) 0x2e };

    private static final byte[] KOREAN_LONG_TEXT_NO_EXPLICIT_ESCSEQ_BYTES = {
            (byte) 0x1b, (byte) 0x24, (byte) 0x29, (byte) 0x43, (byte) 0x54,
            (byte) 0x68, (byte) 0x65, (byte) 0x20, (byte) 0x31, (byte) 0x73,
            (byte) 0x74, (byte) 0x20, (byte) 0x6c, (byte) 0x69, (byte) 0x6e,
            (byte) 0x65, (byte) 0x20, (byte) 0x69, (byte) 0x6e, (byte) 0x63,
            (byte) 0x6c, (byte) 0x75, (byte) 0x64, (byte) 0x65, (byte) 0x73,
            (byte) 0x20, (byte) 0xb1, (byte) 0xe6, (byte) 0xb5, (byte) 0xbf,
            (byte) 0x2e, (byte) 0x0d, (byte) 0x0a, (byte) 0x1b, (byte) 0x24,
            (byte) 0x29, (byte) 0x43, (byte) 0x54, (byte) 0x68, (byte) 0x65,
            (byte) 0x20, (byte) 0x32, (byte) 0x6e, (byte) 0x64, (byte) 0x20,
            (byte) 0x6c, (byte) 0x69, (byte) 0x6e, (byte) 0x65, (byte) 0x20,
            (byte) 0x69, (byte) 0x6e, (byte) 0x63, (byte) 0x6c, (byte) 0x75,
            (byte) 0x64, (byte) 0x65, (byte) 0x73, (byte) 0x20, (byte) 0xb1,
            (byte) 0xe6, (byte) 0xb5, (byte) 0xbf, (byte) 0x2c, (byte) 0x20,
            (byte) 0x74, (byte) 0x6f, (byte) 0x6f, (byte) 0x2e, (byte) 0x0d,
            (byte) 0x0a, (byte) 0x54, (byte) 0x68, (byte) 0x65, (byte) 0x20,
            (byte) 0x33, (byte) 0x72, (byte) 0x64, (byte) 0x20, (byte) 0x6c,
            (byte) 0x69, (byte) 0x6e, (byte) 0x65, (byte) 0x2e };

    private static final byte[] CHINESE_PERSON_NAME_GB2312_BYTES = {
            (byte) 0x5A, (byte) 0x68, (byte) 0x61, (byte) 0x6E, (byte) 0x67,
            (byte) 0x5E, (byte) 0x58, (byte) 0x69, (byte) 0x61, (byte) 0x6F,
            (byte) 0x44, (byte) 0x6F, (byte) 0x6E, (byte) 0x67, (byte) 0x3D,
            (byte) 0x1B, (byte) 0x24, (byte) 0x29, (byte) 0x41, (byte) 0xD5,
            (byte) 0xC5, (byte) 0x5E, (byte) 0x1B, (byte) 0x24, (byte) 0x29,
            (byte) 0x41, (byte) 0xD0, (byte) 0xA1, (byte) 0xB6, (byte) 0xAB,
            (byte) 0x3D };

    private static final byte[] CHINESE_LONG_TEXT_GB2312_BYTES = {
            (byte) 0x31, (byte) 0x2e, (byte) 0x1B, (byte) 0x24, (byte) 0x29,
            (byte) 0x41, (byte) 0xB5, (byte) 0xDA, (byte) 0xD2, (byte) 0xBB,
            (byte) 0xD0, (byte) 0xD0, (byte) 0xCE, (byte) 0xC4, (byte) 0xD7,
            (byte) 0xD6, (byte) 0xA1, (byte) 0xA3, (byte) 0x0D, (byte) 0x0A,
            (byte) 0x32, (byte) 0x2e, (byte) 0x1B, (byte) 0x24, (byte) 0x29,
            (byte) 0x41, (byte) 0xB5, (byte) 0xDA, (byte) 0xB6, (byte) 0xFE,
            (byte) 0xD0, (byte) 0xD0, (byte) 0xCE, (byte) 0xC4, (byte) 0xD7,
            (byte) 0xD6, (byte) 0xA1, (byte) 0xA3, (byte) 0x0D, (byte) 0x0A,
            (byte) 0x33, (byte) 0x2e, (byte) 0x1B, (byte) 0x24, (byte) 0x29,
            (byte) 0x41, (byte) 0xB5, (byte) 0xDA, (byte) 0xC8, (byte) 0xFD,
            (byte) 0xD0, (byte) 0xD0, (byte) 0xCE, (byte) 0xC4, (byte) 0xD7,
            (byte) 0xD6, (byte) 0xA1, (byte) 0xA3, (byte) 0x0D, (byte) 0x0A };

    private static final byte[] CHINESE_PERSON_NAME_UTF8_BYTES = {
            (byte) 0x57, (byte) 0x61, (byte) 0x6e, (byte) 0x67, (byte) 0x5e,
            (byte) 0x58, (byte) 0x69, (byte) 0x61, (byte) 0x6f, (byte) 0x44,
            (byte) 0x6f, (byte) 0x6e, (byte) 0x67, (byte) 0x3d, (byte) 0xe7,
            (byte) 0x8e, (byte) 0x8b, (byte) 0x5e, (byte) 0xe5, (byte) 0xb0,
            (byte) 0x8f, (byte) 0xe6, (byte) 0x9d, (byte) 0xb1, (byte) 0x3d };

    private static final byte[] CHINESE_PERSON_NAME_GB18030_BYTES = {
            (byte) 0x57, (byte) 0x61, (byte) 0x6e, (byte) 0x67, (byte) 0x5e,
            (byte) 0x58, (byte) 0x69, (byte) 0x61, (byte) 0x6f, (byte) 0x44,
            (byte) 0x6f, (byte) 0x6e, (byte) 0x67, (byte) 0x3d, (byte) 0xcd,
            (byte) 0xf5, (byte) 0x5e, (byte) 0xd0, (byte) 0xa1, (byte) 0xb6,
            (byte) 0xab, (byte) 0x3d };

    private static final byte[] FINNISH_PERSON_NAME_8859_15_BYTES = {
            (byte) 0xa6, (byte) 0x69, (byte) 0xa8, (byte) 0x6b, (byte) 0x69,
            (byte) 0x6e, (byte) 0x6f, (byte) 0x5e, (byte) 0xb4, (byte) 0x75,
            (byte) 0xb8, (byte) 0x61 };

    private static final byte[] FINNISH_PERSON_NAME_WINDOWS_1252_BYTE = {
            (byte) 0x8a, (byte) 0x69, (byte) 0x9a, (byte) 0x6b, (byte) 0x69,
            (byte) 0x6e, (byte) 0x6f, (byte) 0x5e, (byte) 0x8e, (byte) 0x75,
            (byte) 0x9e, (byte) 0x61 };

    private SpecificCharacterSet iso8859_1() {
        return SpecificCharacterSet.valueOf(new String[] { "ISO_IR 100" });
    }

    private SpecificCharacterSet iso8859_5() {
        return SpecificCharacterSet.valueOf(new String[] { "ISO_IR 144" });
    }

    private SpecificCharacterSet iso8859_6() {
        return SpecificCharacterSet.valueOf(new String[] { "ISO_IR 127" });
    }

    private SpecificCharacterSet iso8859_7() {
        return SpecificCharacterSet.valueOf(new String[] { "ISO_IR 126" });
    }

    private SpecificCharacterSet iso8859_8() {
        return SpecificCharacterSet.valueOf(new String[] { "ISO_IR 138" });
    }

    private SpecificCharacterSet jisX0208() {
        return SpecificCharacterSet.valueOf(
                new String[] { null, "ISO 2022 IR 87" });
    }

    private SpecificCharacterSet jisX0201() {
        return SpecificCharacterSet.valueOf(
                new String[] { "ISO 2022 IR 13", "ISO 2022 IR 87" });
    }

    private SpecificCharacterSet jisX0201_withAlias() {
        return SpecificCharacterSet.valueOf(
                new String[] { "ISO_IR 13", "ISO 2022 IR 87" });
    }

    private SpecificCharacterSet ksx1001() {
        return SpecificCharacterSet.valueOf(
                new String[] { null, "ISO 2022 IR 149" });
    }

    private SpecificCharacterSet gb2312() {
        return SpecificCharacterSet.valueOf(
                new String[] { null, "ISO 2022 IR 58" });
    }

    private SpecificCharacterSet utf8() {
        return SpecificCharacterSet.valueOf(new String[] { "ISO_IR 192" });
    }

    private SpecificCharacterSet gb18030() {
        return SpecificCharacterSet.valueOf(new String[] { "GB18030" });
    }

    private SpecificCharacterSet gbk() {
        return SpecificCharacterSet.valueOf(new String[] { "GBK" });
    }

    @Test
    public void testEncodeGermanPersonName() {
        assertArrayEquals(GERMAN_PERSON_NAME_BYTE,
                iso8859_1().encode(GERMAN_PERSON_NAME, PN_DELIMS));
    }

    @Test
    public void testDecodeGermanPersonName() {
        assertEquals(GERMAN_PERSON_NAME,
                iso8859_1().decode(GERMAN_PERSON_NAME_BYTE, PN_DELIMS));
    }

    @Test
    public void testEncodeFrenchPersonName() {
        assertArrayEquals(FRENCH_PERSON_NAME_BYTE,
                iso8859_1().encode(FRENCH_PERSON_NAME, PN_DELIMS));
    }

    @Test
    public void testDecodeFrenchPersonName() {
        assertEquals(FRENCH_PERSON_NAME,
                iso8859_1().decode(FRENCH_PERSON_NAME_BYTE, PN_DELIMS));
    }

    @Test
    public void testEncodeRussianPersonName() {
        assertArrayEquals(RUSSIAN_PERSON_NAME_BYTE,
                iso8859_5().encode(RUSSIAN_PERSON_NAME, PN_DELIMS));
    }

    @Test
    public void testDecodeRussianPersonName() {
        assertEquals(RUSSIAN_PERSON_NAME,
                iso8859_5().decode(RUSSIAN_PERSON_NAME_BYTE, PN_DELIMS));
    }

    @Test
    public void testEncodeArabicPersonName() {
        assertArrayEquals(ARABIC_PERSON_NAME_BYTE,
                iso8859_6().encode(ARABIC_PERSON_NAME, PN_DELIMS));
    }

    @Test
    public void testDecodeArabicPersonName() {
        assertEquals(ARABIC_PERSON_NAME,
                iso8859_6().decode(ARABIC_PERSON_NAME_BYTE, PN_DELIMS));
    }

    @Test
    public void testEncodeGreekPersonName() {
        assertArrayEquals(GREEK_PERSON_NAME_BYTE,
                iso8859_7().encode(GREEK_PERSON_NAME, PN_DELIMS));
    }

    @Test
    public void testDecodeGreekPersonName() {
        assertEquals(GREEK_PERSON_NAME,
                iso8859_7().decode(GREEK_PERSON_NAME_BYTE, PN_DELIMS));
    }

    @Test
    public void testEncodeHebrewPersonName() {
        assertArrayEquals(HEBREW_PERSON_NAME_BYTE,
                iso8859_8().encode(HEBREW_PERSON_NAME, PN_DELIMS));
    }

    @Test
    public void testDecodeHebrewPersonName() {
        assertEquals(HEBREW_PERSON_NAME,
                iso8859_8().decode(HEBREW_PERSON_NAME_BYTE, PN_DELIMS));
    }

    @Test
    public void testEncodeJapanesePersonNameASCII() {
        assertArrayEquals(JAPANESE_PERSON_NAME_ASCII_BYTES,
                jisX0208().encode(JAPANESE_PERSON_NAME_ASCII, PN_DELIMS));
    }

    @Test
    public void testDecodeJapanesePersonNameASCII() {
        assertEquals(JAPANESE_PERSON_NAME_ASCII,
                jisX0208().decode(JAPANESE_PERSON_NAME_ASCII_BYTES, PN_DELIMS));
    }

     @Test
    public void testEncodeJapanesePersonNameJISX0201() {
        assertArrayEquals(JAPANESE_PERSON_NAME_JISX0201_BYTES,
                jisX0201().encode(JAPANESE_PERSON_NAME_JISX0201, PN_DELIMS));
    }

    @Test
    public void testDecodeJapanesePersonNameJISX0201() {
        assertEquals(JAPANESE_PERSON_NAME_JISX0201,
                jisX0201().decode(JAPANESE_PERSON_NAME_JISX0201_BYTES, PN_DELIMS));
    }

    @Test
    public void testEncodeJapanesePersonNameJISX0201_withAlias() {
        assertArrayEquals(JAPANESE_PERSON_NAME_JISX0201_BYTES,
                jisX0201_withAlias().encode(JAPANESE_PERSON_NAME_JISX0201, PN_DELIMS));
    }

    @Test
    public void testDecodeJapanesePersonNameJISX0201_withAlias() {
        assertEquals(JAPANESE_PERSON_NAME_JISX0201,
                jisX0201_withAlias().decode(JAPANESE_PERSON_NAME_JISX0201_BYTES, PN_DELIMS));
    }

    @Test
    public void testEncodeKoreanPersonName() {
        assertArrayEquals(KOREAN_PERSON_NAME_BYTES,
                ksx1001().encode(KOREAN_PERSON_NAME, PN_DELIMS));
    }

    @Test
    public void testDecodeKoreanPersonName() {
        assertEquals(KOREAN_PERSON_NAME,
                ksx1001().decode(KOREAN_PERSON_NAME_BYTES, PN_DELIMS));
    }

    @Test
    public void testEncodeKoreanLongText() {
        assertArrayEquals(KOREAN_LONG_TEXT_BYTES,
                ksx1001().encode(KOREAN_LONG_TEXT, LT_DELIMS));
    }
    
    @Test
    public void testDecodeKoreanLongText() {
        assertEquals(KOREAN_LONG_TEXT,
                ksx1001().decode(KOREAN_LONG_TEXT_NO_EXPLICIT_ESCSEQ_BYTES, LT_DELIMS));
    }

    @Test
    public void testEncodeChinesePersonNameGB2312() {
        assertArrayEquals(CHINESE_PERSON_NAME_GB2312_BYTES,
                gb2312().encode(CHINESE_PERSON_NAME_GB2312, PN_DELIMS));
    }

    @Test
    public void testDecodeChinesePersonNameGB2312() {
        assertEquals(CHINESE_PERSON_NAME_GB2312,
                gb2312().decode(CHINESE_PERSON_NAME_GB2312_BYTES, PN_DELIMS));
    }

    @Test
    public void testEncodeChineseLongTextGB2312() {
        assertArrayEquals(CHINESE_LONG_TEXT_GB2312_BYTES,
                gb2312().encode(CHINESE_LONG_TEXT_GB2312, LT_DELIMS));
    }

    @Test
    public void testDecodeChineseLongTextGB2312() {
        assertEquals(CHINESE_LONG_TEXT_GB2312,
                gb2312().decode(CHINESE_LONG_TEXT_GB2312_BYTES, LT_DELIMS));
    }

    @Test
    public void testEncodeChinesePersonNameUTF8() {
        assertArrayEquals(CHINESE_PERSON_NAME_UTF8_BYTES,
                utf8().encode(CHINESE_PERSON_NAME_UTF8, PN_DELIMS));
    }

    @Test
    public void testDecodeChinesePersonNameUTF8() {
        assertEquals(CHINESE_PERSON_NAME_UTF8,
                utf8().decode(CHINESE_PERSON_NAME_UTF8_BYTES, PN_DELIMS));
    }

    @Test
    public void testEncodeChinesePersonNameGB18030() {
        assertArrayEquals(CHINESE_PERSON_NAME_GB18030_BYTES,
                gb18030().encode(CHINESE_PERSON_NAME_GB18030, PN_DELIMS));
    }

    @Test
    public void testDecodeChinesePersonNameGB18030() {
        assertEquals(CHINESE_PERSON_NAME_GB18030,
                gb18030().decode(CHINESE_PERSON_NAME_GB18030_BYTES, PN_DELIMS));
    }

    @Test
    public void testEncodeChinesePersonNameGBK() {
        assertArrayEquals(CHINESE_PERSON_NAME_GB18030_BYTES,
                gbk().encode(CHINESE_PERSON_NAME_GB18030, PN_DELIMS));
    }

    @Test
    public void testDecodeChinesePersonNameGBK() {
        assertEquals(CHINESE_PERSON_NAME_GB18030,
                gbk().decode(CHINESE_PERSON_NAME_GB18030_BYTES, PN_DELIMS));
    }
    
    @Test
    public void testDecodeJISX0201EdgeCases() {
        // Cover some illegal/undefined cases to verify that they do not result in exceptions. 
        // Some of the resulting strings are not meaningful.
        byte[][] edgeCases = new byte[][] {
                {},
                { 0x33, 0x43 },
                { 0x33, 0x1b },
                { 0x1b, 0x24, 0x28 },
                { 0x1b, 0x24, 0x28, 0x55 },
                { 0x1b, 0x24, 0x28, 0x44 },
                { 0x1b, 0x24, 0x28, 0x44, 0x55 },
                { 0x1b, 0x24, 0x29 },
                { 0x1b, 0x24, 0x29, 0x41 },
                { 0x1b, 0x24, 0x29, 0x41, 0x55 },
                { 0x1b, 0x24, 0x29, 0x43 },
                { 0x1b, 0x24, 0x29, 0x43, 0x55 },
                { 0x1b, 0x24, 0x29, 0x55 },
                { 0x1b, 0x24, 0x42 },
                { 0x1b, 0x24, 0x42, 0x31 },
                { 0x1b, 0x33 },
                { 0x1b, 0x33, 0x43 } };
        final String[] expectedStrings = new String[] {
                "",
                "3C",
                "3\u001b",
                "\u001b$(",
                "\u001b$(U",
                "",
                "\ufffd",
                "\u001b$)",
                "",
                "U",
                "",
                "U",
                "\u001b$)U",
                "",
                "\ufffd",
                "\u001b3",
                "\u001b3C" };
        assertEquals( "There must be the same number of expected results and edge cases.", edgeCases.length,
                      expectedStrings.length );
        for ( int i = 0; i < edgeCases.length; ++i ) {
            assertEquals( "Unexpected value for the sequence with the index " + i, expectedStrings[ i ],
                          jisX0201().decode( edgeCases[ i ], LT_DELIMS ) );
        }
    }

    @Test
    public void testEncodeFinnishPersonName8859_15() {
        SpecificCharacterSet.setCharsetNameMapping("ISO_IR 100", "ISO-8859-15");
        try {
            assertArrayEquals(FINNISH_PERSON_NAME_8859_15_BYTES,
                    iso8859_1().encode(FINNISH_PERSON_NAME, PN_DELIMS));
        } finally {
            SpecificCharacterSet.resetCharsetNameMappings();
        }
    }

    @Test
    public void testDecodeFinnishPersonName8859_15() {
        SpecificCharacterSet.setCharsetNameMapping("ISO_IR 100", "ISO-8859-15");
        try {
            assertEquals(FINNISH_PERSON_NAME,
                    iso8859_1().decode(FINNISH_PERSON_NAME_8859_15_BYTES, PN_DELIMS));
        } finally {
            SpecificCharacterSet.resetCharsetNameMappings();
        }
    }

    @Test
    public void testEncodeFinnishPersonNameWindows1252() {
        SpecificCharacterSet.setCharsetNameMapping("ISO_IR 100", "windows-1252");
        try {
            assertArrayEquals(FINNISH_PERSON_NAME_WINDOWS_1252_BYTE,
                    iso8859_1().encode(FINNISH_PERSON_NAME, PN_DELIMS));
        } finally {
            SpecificCharacterSet.resetCharsetNameMappings();
        }
    }

    @Test
    public void testDecodeFinnishPersonNameWindows1252() {
        SpecificCharacterSet.setCharsetNameMapping("ISO_IR 100", "windows-1252");
        try {
            assertEquals(FINNISH_PERSON_NAME,
                    iso8859_1().decode(FINNISH_PERSON_NAME_WINDOWS_1252_BYTE, PN_DELIMS));
        } finally {
            SpecificCharacterSet.resetCharsetNameMappings();
        }
    }
}
