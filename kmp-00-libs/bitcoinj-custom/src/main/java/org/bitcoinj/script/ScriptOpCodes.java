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
    private static final Map<Integer, String> opCodeMap = ImmutableMap.builder().put((Object)0, (Object)"0").put((Object)76, (Object)"PUSHDATA1").put((Object)77, (Object)"PUSHDATA2").put((Object)78, (Object)"PUSHDATA4").put((Object)79, (Object)"1NEGATE").put((Object)80, (Object)"RESERVED").put((Object)81, (Object)"1").put((Object)82, (Object)"2").put((Object)83, (Object)"3").put((Object)84, (Object)"4").put((Object)85, (Object)"5").put((Object)86, (Object)"6").put((Object)87, (Object)"7").put((Object)88, (Object)"8").put((Object)89, (Object)"9").put((Object)90, (Object)"10").put((Object)91, (Object)"11").put((Object)92, (Object)"12").put((Object)93, (Object)"13").put((Object)94, (Object)"14").put((Object)95, (Object)"15").put((Object)96, (Object)"16").put((Object)97, (Object)"NOP").put((Object)98, (Object)"VER").put((Object)99, (Object)"IF").put((Object)100, (Object)"NOTIF").put((Object)101, (Object)"VERIF").put((Object)102, (Object)"VERNOTIF").put((Object)103, (Object)"ELSE").put((Object)104, (Object)"ENDIF").put((Object)105, (Object)"VERIFY").put((Object)106, (Object)"RETURN").put((Object)107, (Object)"TOALTSTACK").put((Object)108, (Object)"FROMALTSTACK").put((Object)109, (Object)"2DROP").put((Object)110, (Object)"2DUP").put((Object)111, (Object)"3DUP").put((Object)112, (Object)"2OVER").put((Object)113, (Object)"2ROT").put((Object)114, (Object)"2SWAP").put((Object)115, (Object)"IFDUP").put((Object)116, (Object)"DEPTH").put((Object)117, (Object)"DROP").put((Object)118, (Object)"DUP").put((Object)119, (Object)"NIP").put((Object)120, (Object)"OVER").put((Object)121, (Object)"PICK").put((Object)122, (Object)"ROLL").put((Object)123, (Object)"ROT").put((Object)124, (Object)"SWAP").put((Object)125, (Object)"TUCK").put((Object)126, (Object)"CAT").put((Object)127, (Object)"SUBSTR").put((Object)128, (Object)"LEFT").put((Object)129, (Object)"RIGHT").put((Object)130, (Object)"SIZE").put((Object)131, (Object)"INVERT").put((Object)132, (Object)"AND").put((Object)133, (Object)"OR").put((Object)134, (Object)"XOR").put((Object)135, (Object)"EQUAL").put((Object)136, (Object)"EQUALVERIFY").put((Object)137, (Object)"RESERVED1").put((Object)138, (Object)"RESERVED2").put((Object)139, (Object)"1ADD").put((Object)140, (Object)"1SUB").put((Object)141, (Object)"2MUL").put((Object)142, (Object)"2DIV").put((Object)143, (Object)"NEGATE").put((Object)144, (Object)"ABS").put((Object)145, (Object)"NOT").put((Object)146, (Object)"0NOTEQUAL").put((Object)147, (Object)"ADD").put((Object)148, (Object)"SUB").put((Object)149, (Object)"MUL").put((Object)150, (Object)"DIV").put((Object)151, (Object)"MOD").put((Object)152, (Object)"LSHIFT").put((Object)153, (Object)"RSHIFT").put((Object)154, (Object)"BOOLAND").put((Object)155, (Object)"BOOLOR").put((Object)156, (Object)"NUMEQUAL").put((Object)157, (Object)"NUMEQUALVERIFY").put((Object)158, (Object)"NUMNOTEQUAL").put((Object)159, (Object)"LESSTHAN").put((Object)160, (Object)"GREATERTHAN").put((Object)161, (Object)"LESSTHANOREQUAL").put((Object)162, (Object)"GREATERTHANOREQUAL").put((Object)163, (Object)"MIN").put((Object)164, (Object)"MAX").put((Object)165, (Object)"WITHIN").put((Object)166, (Object)"RIPEMD160").put((Object)167, (Object)"SHA1").put((Object)168, (Object)"SHA256").put((Object)169, (Object)"HASH160").put((Object)170, (Object)"HASH256").put((Object)171, (Object)"CODESEPARATOR").put((Object)172, (Object)"CHECKSIG").put((Object)173, (Object)"CHECKSIGVERIFY").put((Object)174, (Object)"CHECKMULTISIG").put((Object)175, (Object)"CHECKMULTISIGVERIFY").put((Object)176, (Object)"NOP1").put((Object)177, (Object)"CHECKLOCKTIMEVERIFY").put((Object)178, (Object)"CHECKSEQUENCEVERIFY").put((Object)179, (Object)"NOP4").put((Object)180, (Object)"NOP5").put((Object)181, (Object)"NOP6").put((Object)182, (Object)"NOP7").put((Object)183, (Object)"NOP8").put((Object)184, (Object)"NOP9").put((Object)185, (Object)"NOP10").build();
    private static final Map<String, Integer> opCodeNameMap = ImmutableMap.builder().put((Object)"0", (Object)0).put((Object)"PUSHDATA1", (Object)76).put((Object)"PUSHDATA2", (Object)77).put((Object)"PUSHDATA4", (Object)78).put((Object)"1NEGATE", (Object)79).put((Object)"RESERVED", (Object)80).put((Object)"1", (Object)81).put((Object)"2", (Object)82).put((Object)"3", (Object)83).put((Object)"4", (Object)84).put((Object)"5", (Object)85).put((Object)"6", (Object)86).put((Object)"7", (Object)87).put((Object)"8", (Object)88).put((Object)"9", (Object)89).put((Object)"10", (Object)90).put((Object)"11", (Object)91).put((Object)"12", (Object)92).put((Object)"13", (Object)93).put((Object)"14", (Object)94).put((Object)"15", (Object)95).put((Object)"16", (Object)96).put((Object)"NOP", (Object)97).put((Object)"VER", (Object)98).put((Object)"IF", (Object)99).put((Object)"NOTIF", (Object)100).put((Object)"VERIF", (Object)101).put((Object)"VERNOTIF", (Object)102).put((Object)"ELSE", (Object)103).put((Object)"ENDIF", (Object)104).put((Object)"VERIFY", (Object)105).put((Object)"RETURN", (Object)106).put((Object)"TOALTSTACK", (Object)107).put((Object)"FROMALTSTACK", (Object)108).put((Object)"2DROP", (Object)109).put((Object)"2DUP", (Object)110).put((Object)"3DUP", (Object)111).put((Object)"2OVER", (Object)112).put((Object)"2ROT", (Object)113).put((Object)"2SWAP", (Object)114).put((Object)"IFDUP", (Object)115).put((Object)"DEPTH", (Object)116).put((Object)"DROP", (Object)117).put((Object)"DUP", (Object)118).put((Object)"NIP", (Object)119).put((Object)"OVER", (Object)120).put((Object)"PICK", (Object)121).put((Object)"ROLL", (Object)122).put((Object)"ROT", (Object)123).put((Object)"SWAP", (Object)124).put((Object)"TUCK", (Object)125).put((Object)"CAT", (Object)126).put((Object)"SUBSTR", (Object)127).put((Object)"LEFT", (Object)128).put((Object)"RIGHT", (Object)129).put((Object)"SIZE", (Object)130).put((Object)"INVERT", (Object)131).put((Object)"AND", (Object)132).put((Object)"OR", (Object)133).put((Object)"XOR", (Object)134).put((Object)"EQUAL", (Object)135).put((Object)"EQUALVERIFY", (Object)136).put((Object)"RESERVED1", (Object)137).put((Object)"RESERVED2", (Object)138).put((Object)"1ADD", (Object)139).put((Object)"1SUB", (Object)140).put((Object)"2MUL", (Object)141).put((Object)"2DIV", (Object)142).put((Object)"NEGATE", (Object)143).put((Object)"ABS", (Object)144).put((Object)"NOT", (Object)145).put((Object)"0NOTEQUAL", (Object)146).put((Object)"ADD", (Object)147).put((Object)"SUB", (Object)148).put((Object)"MUL", (Object)149).put((Object)"DIV", (Object)150).put((Object)"MOD", (Object)151).put((Object)"LSHIFT", (Object)152).put((Object)"RSHIFT", (Object)153).put((Object)"BOOLAND", (Object)154).put((Object)"BOOLOR", (Object)155).put((Object)"NUMEQUAL", (Object)156).put((Object)"NUMEQUALVERIFY", (Object)157).put((Object)"NUMNOTEQUAL", (Object)158).put((Object)"LESSTHAN", (Object)159).put((Object)"GREATERTHAN", (Object)160).put((Object)"LESSTHANOREQUAL", (Object)161).put((Object)"GREATERTHANOREQUAL", (Object)162).put((Object)"MIN", (Object)163).put((Object)"MAX", (Object)164).put((Object)"WITHIN", (Object)165).put((Object)"RIPEMD160", (Object)166).put((Object)"SHA1", (Object)167).put((Object)"SHA256", (Object)168).put((Object)"HASH160", (Object)169).put((Object)"HASH256", (Object)170).put((Object)"CODESEPARATOR", (Object)171).put((Object)"CHECKSIG", (Object)172).put((Object)"CHECKSIGVERIFY", (Object)173).put((Object)"CHECKMULTISIG", (Object)174).put((Object)"CHECKMULTISIGVERIFY", (Object)175).put((Object)"NOP1", (Object)176).put((Object)"CHECKLOCKTIMEVERIFY", (Object)177).put((Object)"CHECKSEQUENCEVERIFY", (Object)178).put((Object)"NOP2", (Object)177).put((Object)"NOP3", (Object)178).put((Object)"NOP4", (Object)179).put((Object)"NOP5", (Object)180).put((Object)"NOP6", (Object)181).put((Object)"NOP7", (Object)182).put((Object)"NOP8", (Object)183).put((Object)"NOP9", (Object)184).put((Object)"NOP10", (Object)185).build();

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

