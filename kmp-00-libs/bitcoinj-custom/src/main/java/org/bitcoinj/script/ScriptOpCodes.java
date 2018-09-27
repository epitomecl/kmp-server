/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 */
package org.bitcoinj.script;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

public class ScriptOpCodes {
    public static final int OP_0 = 0;
    public static final int OP_FALSE = 0;
    public static final int OP_PUSHDATA1 = 76;
    public static final int OP_PUSHDATA2 = 77;
    public static final int OP_PUSHDATA4 = 78;
    public static final int OP_1NEGATE = 79;
    public static final int OP_RESERVED = 80;
    public static final int OP_1 = 81;
    public static final int OP_TRUE = 81;
    public static final int OP_2 = 82;
    public static final int OP_3 = 83;
    public static final int OP_4 = 84;
    public static final int OP_5 = 85;
    public static final int OP_6 = 86;
    public static final int OP_7 = 87;
    public static final int OP_8 = 88;
    public static final int OP_9 = 89;
    public static final int OP_10 = 90;
    public static final int OP_11 = 91;
    public static final int OP_12 = 92;
    public static final int OP_13 = 93;
    public static final int OP_14 = 94;
    public static final int OP_15 = 95;
    public static final int OP_16 = 96;
    public static final int OP_NOP = 97;
    public static final int OP_VER = 98;
    public static final int OP_IF = 99;
    public static final int OP_NOTIF = 100;
    public static final int OP_VERIF = 101;
    public static final int OP_VERNOTIF = 102;
    public static final int OP_ELSE = 103;
    public static final int OP_ENDIF = 104;
    public static final int OP_VERIFY = 105;
    public static final int OP_RETURN = 106;
    public static final int OP_TOALTSTACK = 107;
    public static final int OP_FROMALTSTACK = 108;
    public static final int OP_2DROP = 109;
    public static final int OP_2DUP = 110;
    public static final int OP_3DUP = 111;
    public static final int OP_2OVER = 112;
    public static final int OP_2ROT = 113;
    public static final int OP_2SWAP = 114;
    public static final int OP_IFDUP = 115;
    public static final int OP_DEPTH = 116;
    public static final int OP_DROP = 117;
    public static final int OP_DUP = 118;
    public static final int OP_NIP = 119;
    public static final int OP_OVER = 120;
    public static final int OP_PICK = 121;
    public static final int OP_ROLL = 122;
    public static final int OP_ROT = 123;
    public static final int OP_SWAP = 124;
    public static final int OP_TUCK = 125;
    public static final int OP_CAT = 126;
    public static final int OP_SUBSTR = 127;
    public static final int OP_LEFT = 128;
    public static final int OP_RIGHT = 129;
    public static final int OP_SIZE = 130;
    public static final int OP_INVERT = 131;
    public static final int OP_AND = 132;
    public static final int OP_OR = 133;
    public static final int OP_XOR = 134;
    public static final int OP_EQUAL = 135;
    public static final int OP_EQUALVERIFY = 136;
    public static final int OP_RESERVED1 = 137;
    public static final int OP_RESERVED2 = 138;
    public static final int OP_1ADD = 139;
    public static final int OP_1SUB = 140;
    public static final int OP_2MUL = 141;
    public static final int OP_2DIV = 142;
    public static final int OP_NEGATE = 143;
    public static final int OP_ABS = 144;
    public static final int OP_NOT = 145;
    public static final int OP_0NOTEQUAL = 146;
    public static final int OP_ADD = 147;
    public static final int OP_SUB = 148;
    public static final int OP_MUL = 149;
    public static final int OP_DIV = 150;
    public static final int OP_MOD = 151;
    public static final int OP_LSHIFT = 152;
    public static final int OP_RSHIFT = 153;
    public static final int OP_BOOLAND = 154;
    public static final int OP_BOOLOR = 155;
    public static final int OP_NUMEQUAL = 156;
    public static final int OP_NUMEQUALVERIFY = 157;
    public static final int OP_NUMNOTEQUAL = 158;
    public static final int OP_LESSTHAN = 159;
    public static final int OP_GREATERTHAN = 160;
    public static final int OP_LESSTHANOREQUAL = 161;
    public static final int OP_GREATERTHANOREQUAL = 162;
    public static final int OP_MIN = 163;
    public static final int OP_MAX = 164;
    public static final int OP_WITHIN = 165;
    public static final int OP_RIPEMD160 = 166;
    public static final int OP_SHA1 = 167;
    public static final int OP_SHA256 = 168;
    public static final int OP_HASH160 = 169;
    public static final int OP_HASH256 = 170;
    public static final int OP_CODESEPARATOR = 171;
    public static final int OP_CHECKSIG = 172;
    public static final int OP_CHECKSIGVERIFY = 173;
    public static final int OP_CHECKMULTISIG = 174;
    public static final int OP_CHECKMULTISIGVERIFY = 175;
    public static final int OP_CHECKLOCKTIMEVERIFY = 177;
    public static final int OP_CHECKSEQUENCEVERIFY = 178;
    public static final int OP_NOP1 = 176;
    @Deprecated
    public static final int OP_NOP2 = 177;
    @Deprecated
    public static final int OP_NOP3 = 178;
    public static final int OP_NOP4 = 179;
    public static final int OP_NOP5 = 180;
    public static final int OP_NOP6 = 181;
    public static final int OP_NOP7 = 182;
    public static final int OP_NOP8 = 183;
    public static final int OP_NOP9 = 184;
    public static final int OP_NOP10 = 185;
    public static final int OP_INVALIDOPCODE = 255;
    private static final Map<Integer, String> opCodeMap = ImmutableMap.<Integer, String>builder().put(0, "0").put(76, "PUSHDATA1").put(77, "PUSHDATA2").put(78, "PUSHDATA4").put(79, "1NEGATE").put(80, "RESERVED").put(81, "1").put(82, "2").put(83, "3").put(84, "4").put(85, "5").put(86, "6").put(87, "7").put(88, "8").put(89, "9").put(90, "10").put(91, "11").put(92, "12").put(93, "13").put(94, "14").put(95, "15").put(96, "16").put(97, "NOP").put(98, "VER").put(99, "IF").put(100, "NOTIF").put(101, "VERIF").put(102, "VERNOTIF").put(103, "ELSE").put(104, "ENDIF").put(105, "VERIFY").put(106, "RETURN").put(107, "TOALTSTACK").put(108, "FROMALTSTACK").put(109, "2DROP").put(110, "2DUP").put(111, "3DUP").put(112, "2OVER").put(113, "2ROT").put(114, "2SWAP").put(115, "IFDUP").put(116, "DEPTH").put(117, "DROP").put(118, "DUP").put(119, "NIP").put(120, "OVER").put(121, "PICK").put(122, "ROLL").put(123, "ROT").put(124, "SWAP").put(125, "TUCK").put(126, "CAT").put(127, "SUBSTR").put(128, "LEFT").put(129, "RIGHT").put(130, "SIZE").put(131, "INVERT").put(132, "AND").put(133, "OR").put(134, "XOR").put(135, "EQUAL").put(136, "EQUALVERIFY").put(137, "RESERVED1").put(138, "RESERVED2").put(139, "1ADD").put(140, "1SUB").put(141, "2MUL").put(142, "2DIV").put(143, "NEGATE").put(144, "ABS").put(145, "NOT").put(146, "0NOTEQUAL").put(147, "ADD").put(148, "SUB").put(149, "MUL").put(150, "DIV").put(151, "MOD").put(152, "LSHIFT").put(153, "RSHIFT").put(154, "BOOLAND").put(155, "BOOLOR").put(156, "NUMEQUAL").put(157, "NUMEQUALVERIFY").put(158, "NUMNOTEQUAL").put(159, "LESSTHAN").put(160, "GREATERTHAN").put(161, "LESSTHANOREQUAL").put(162, "GREATERTHANOREQUAL").put(163, "MIN").put(164, "MAX").put(165, "WITHIN").put(166, "RIPEMD160").put(167, "SHA1").put(168, "SHA256").put(169, "HASH160").put(170, "HASH256").put(171, "CODESEPARATOR").put(172, "CHECKSIG").put(173, "CHECKSIGVERIFY").put(174, "CHECKMULTISIG").put(175, "CHECKMULTISIGVERIFY").put(176, "NOP1").put(177, "CHECKLOCKTIMEVERIFY").put(178, "CHECKSEQUENCEVERIFY").put(179, "NOP4").put(180, "NOP5").put(181, "NOP6").put(182, "NOP7").put(183, "NOP8").put(184, "NOP9").put(185, "NOP10").build();
    private static final Map<String, Integer> opCodeNameMap = ImmutableMap.<String, Integer>builder().put("0", 0).put("PUSHDATA1", 76).put("PUSHDATA2", 77).put("PUSHDATA4", 78).put("1NEGATE", 79).put("RESERVED", 80).put("1", 81).put("2", 82).put("3", 83).put("4", 84).put("5", 85).put("6", 86).put("7", 87).put("8", 88).put("9", 89).put("10", 90).put("11", 91).put("12", 92).put("13", 93).put("14", 94).put("15", 95).put("16", 96).put("NOP", 97).put("VER", 98).put("IF", 99).put("NOTIF", 100).put("VERIF", 101).put("VERNOTIF", 102).put("ELSE", 103).put("ENDIF", 104).put("VERIFY", 105).put("RETURN", 106).put("TOALTSTACK", 107).put("FROMALTSTACK", 108).put("2DROP", 109).put("2DUP", 110).put("3DUP", 111).put("2OVER", 112).put("2ROT", 113).put("2SWAP", 114).put("IFDUP", 115).put("DEPTH", 116).put("DROP", 117).put("DUP", 118).put("NIP", 119).put("OVER", 120).put("PICK", 121).put("ROLL", 122).put("ROT", 123).put("SWAP", 124).put("TUCK", 125).put("CAT", 126).put("SUBSTR", 127).put("LEFT", 128).put("RIGHT", 129).put("SIZE", 130).put("INVERT", 131).put("AND", 132).put("OR", 133).put("XOR", 134).put("EQUAL", 135).put("EQUALVERIFY", 136).put("RESERVED1", 137).put("RESERVED2", 138).put("1ADD", 139).put("1SUB", 140).put("2MUL", 141).put("2DIV", 142).put("NEGATE", 143).put("ABS", 144).put("NOT", 145).put("0NOTEQUAL", 146).put("ADD", 147).put("SUB", 148).put("MUL", 149).put("DIV", 150).put("MOD", 151).put("LSHIFT", 152).put("RSHIFT", 153).put("BOOLAND", 154).put("BOOLOR", 155).put("NUMEQUAL", 156).put("NUMEQUALVERIFY", 157).put("NUMNOTEQUAL", 158).put("LESSTHAN", 159).put("GREATERTHAN", 160).put("LESSTHANOREQUAL", 161).put("GREATERTHANOREQUAL", 162).put("MIN", 163).put("MAX", 164).put("WITHIN", 165).put("RIPEMD160", 166).put("SHA1", 167).put("SHA256", 168).put("HASH160", 169).put("HASH256", 170).put("CODESEPARATOR", 171).put("CHECKSIG", 172).put("CHECKSIGVERIFY", 173).put("CHECKMULTISIG", 174).put("CHECKMULTISIGVERIFY", 175).put("NOP1", 176).put("CHECKLOCKTIMEVERIFY", 177).put("CHECKSEQUENCEVERIFY", 178).put("NOP2", 177).put("NOP3", 178).put("NOP4", 179).put("NOP5", 180).put("NOP6", 181).put("NOP7", 182).put("NOP8", 183).put("NOP9", 184).put("NOP10", 185).build();

    public static String getOpCodeName(int opcode) {
        if (opCodeMap.containsKey(opcode)) {
            return opCodeMap.get(opcode);
        }
        return "NON_OP(" + opcode + ")";
    }

    public static String getPushDataName(int opcode) {
        if (opCodeMap.containsKey(opcode)) {
            return opCodeMap.get(opcode);
        }
        return "PUSHDATA(" + opcode + ")";
    }

    public static int getOpCode(String opCodeName) {
        if (opCodeNameMap.containsKey(opCodeName)) {
            return opCodeNameMap.get(opCodeName);
        }
        return 255;
    }
}

