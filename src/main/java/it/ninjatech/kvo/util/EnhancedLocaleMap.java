package it.ninjatech.kvo.util;

import it.ninjatech.kvo.model.EnhancedLocale;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public final class EnhancedLocaleMap {

	private enum FlagCoord {

		AD(32, 4),
		AE(64, 4),
		AF(96, 4),
		AG(128, 4),
		AI(160, 4),
		AL(192, 4),
		AM(224, 4),
		AN(256, 4),
		AO(288, 4),
		AR(320, 4),
		AS(352, 4),
		AT(384, 4),
		AU(416, 4),
		AW(448, 4),
		AZ(0, 36),
		BA(32, 36),
		BB(64, 36),
		BD(96, 36),
		BE(128, 36),
		BF(160, 36),
		BG(192, 36),
		BH(224, 36),
		BI(256, 36),
		BJ(288, 36),
		BM(320, 36),
		BN(352, 36),
		BO(384, 36),
		BR(416, 36),
		BS(448, 36),
		BT(0, 68),
		BW(32, 68),
		BY(64, 68),
		BZ(96, 68),
		CA(128, 68),
		CD(160, 68),
		CF(192, 68),
		CG(224, 68),
		CH(256, 68),
		CI(288, 68),
		CK(320, 68),
		CL(352, 68),
		CM(384, 68),
		CN(416, 68),
		CO(448, 68),
		CR(0, 100),
		CU(32, 100),
		CV(64, 100),
		CY(96, 100),
		CZ(128, 100),
		DE(160, 100),
		DJ(192, 100),
		DK(224, 100),
		DM(256, 100),
		DO(288, 100),
		DZ(320, 100),
		EC(352, 100),
		EE(384, 100),
		EG(416, 100),
		EH(448, 100),
		ER(0, 132),
		ES(32, 132),
		ET(64, 132),
		FI(96, 132),
		FJ(128, 132),
		FM(160, 132),
		FO(192, 132),
		FR(224, 132),
		GA(256, 132),
		GB(288, 132),
		GD(320, 132),
		GE(352, 132),
		GG(384, 132),
		GH(416, 132),
		GI(448, 132),
		GL(0, 164),
		GM(32, 164),
		GN(64, 164),
		GP(96, 164),
		GQ(128, 164),
		GR(160, 164),
		GT(192, 164),
		GU(224, 164),
		GW(256, 164),
		GY(288, 164),
		HK(320, 164),
		HN(352, 164),
		HR(384, 164),
		HT(416, 164),
		HU(448, 164),
		ID(0, 196),
		IE(32, 196),
		IL(64, 196),
		IM(96, 196),
		IN(128, 196),
		IQ(160, 196),
		IR(192, 196),
		IS(224, 196),
		IT(256, 196),
		JE(288, 196),
		JM(320, 196),
		JO(352, 196),
		JP(384, 196),
		KE(416, 196),
		KG(448, 196),
		KH(0, 228),
		KI(32, 228),
		KM(64, 228),
		KN(96, 228),
		KP(128, 228),
		KR(160, 228),
		KW(192, 228),
		KY(224, 228),
		KZ(256, 228),
		LA(288, 228),
		LB(320, 228),
		LC(352, 228),
		LI(384, 228),
		LK(416, 228),
		LR(448, 228),
		LS(0, 260),
		LT(32, 260),
		LU(64, 260),
		LV(96, 260),
		LY(128, 260),
		MA(160, 260),
		MC(192, 260),
		MD(224, 260),
		ME(256, 260),
		MG(288, 260),
		MH(320, 260),
		MK(352, 260),
		ML(384, 260),
		MM(416, 260),
		MN(448, 260),
		MO(0, 292),
		MQ(32, 292),
		MR(64, 292),
		MS(96, 292),
		MT(128, 292),
		MU(160, 292),
		MV(192, 292),
		MW(224, 292),
		MX(256, 292),
		MY(288, 292),
		MZ(320, 292),
		NA(352, 292),
		NC(384, 292),
		NE(416, 292),
		NG(448, 292),
		NI(0, 324),
		NL(32, 324),
		NO(64, 324),
		NP(96, 324),
		NR(128, 324),
		NZ(160, 324),
		OM(192, 324),
		PA(224, 324),
		PE(256, 324),
		PF(288, 324),
		PG(320, 324),
		PH(352, 324),
		PK(384, 324),
		PL(416, 324),
		PR(448, 324),
		PS(0, 356),
		PT(32, 356),
		PW(64, 356),
		PY(96, 356),
		QA(128, 356),
		RE(160, 356),
		RO(192, 356),
		RS(224, 356),
		RU(256, 356),
		RW(288, 356),
		SA(320, 356),
		SB(352, 356),
		SC(384, 356),
		SD(416, 356),
		SE(448, 356),
		SG(0, 388),
		SI(32, 388),
		SK(64, 388),
		SL(96, 388),
		SM(128, 388),
		SN(160, 388),
		SO(192, 388),
		SR(224, 388),
		ST(256, 388),
		SV(288, 388),
		SY(320, 388),
		SZ(352, 388),
		TC(384, 388),
		TD(416, 388),
		TG(448, 388),
		TH(0, 420),
		TJ(32, 420),
		TL(64, 420),
		TM(96, 420),
		TN(128, 420),
		TO(160, 420),
		TR(192, 420),
		TT(224, 420),
		TV(256, 420),
		TW(288, 420),
		TZ(320, 420),
		UA(352, 420),
		UG(384, 420),
		US(416, 420),
		UY(448, 420),
		UZ(0, 452),
		VA(32, 452),
		VC(64, 452),
		VE(96, 452),
		VG(128, 452),
		VI(160, 452),
		VN(192, 452),
		VU(224, 452),
		WS(256, 452),
		YE(288, 452),
		ZA(320, 452),
		ZM(352, 452),
		ZW(384, 452);

		private final int x;
		private final int y;

		private FlagCoord(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	private enum LanguageCountryData {

		AD("ad", "Andorra", FlagCoord.AD, "ca", "Catalan", FlagCoord.AD, true),
		AE("ae", "United Arab Emirates", FlagCoord.AE, "ar", "Arabic", FlagCoord.AE, true),
		AF("af", "Afghanistan", FlagCoord.AF, "fa", "Farsi", FlagCoord.AF, true),
		AG("ag", "Antigua and Barbuda", FlagCoord.AG, "en", "English", FlagCoord.GB, false),
		AI("ai", "Anguilla", FlagCoord.AI, "en", "English", FlagCoord.GB, false),
		AL("al", "Albania", FlagCoord.AL, "sq", "Albanian", FlagCoord.AL, true),
		AM("am", "Armenia", FlagCoord.AM, "hy", "Armenian", FlagCoord.AM, true),
		AN("an", "Netherlands Antilles", FlagCoord.AN, "nl", "Dutch", FlagCoord.NL, false),
		AO("ao", "Angola", FlagCoord.AO, "pt", "Portuguese", FlagCoord.PT, false),
		AR("ar", "Argentina", FlagCoord.AR, "es", "Spanish", FlagCoord.ES, false),
		AS("as", "American Samoa", FlagCoord.AS, "en", "English", FlagCoord.GB, false),
		AT("at", "Austria", FlagCoord.AT, "de", "German", FlagCoord.DE, false),
		AU("au", "Australia", FlagCoord.AU, "en", "English", FlagCoord.GB, false),
		AW("aw", "Aruba", FlagCoord.AW, "nl", "Dutch", FlagCoord.NL, false),
		AZ("az", "Azerbaijan", FlagCoord.AZ, "az", "Arabic", FlagCoord.AZ, true),
		BA("ba", "Bosnia and Herzegovina", FlagCoord.BA, "bs", "Bosnian", FlagCoord.BA, true),
		BB("bb", "Barbados", FlagCoord.BB, "en", "English", FlagCoord.GB, false),
		BD("bd", "Bangladesh", FlagCoord.BD, "bn", "Bengali, Bangla", FlagCoord.BD, true),
		BE("be", "Belgium", FlagCoord.BE, "nl", "Dutch", FlagCoord.NL, false),
		BF("bf", "Burkina Faso", FlagCoord.BF, "fr", "French", FlagCoord.FR, false),
		BG("bg", "Bulgaria", FlagCoord.BG, "bg", "Bulgarian", FlagCoord.BG, true),
		BH("bh", "Bahrain", FlagCoord.BH, "ar", "Arabic", FlagCoord.AE, false),
		BI("bi", "Burundi", FlagCoord.BI, "fr", "French", FlagCoord.FR, false),
		BJ("bj", "Benin", FlagCoord.BJ, "fr", "French", FlagCoord.FR, false),
		BM("bm", "Bermuda", FlagCoord.BM, "en", "English", FlagCoord.GB, false),
		BN("bn", "Brunei Darussalam", FlagCoord.BN, "ms", "Malay", FlagCoord.MY, false),
		BO("bo", "Bolivia (Plurinational State of)", FlagCoord.BO, "es", "Spanish", FlagCoord.ES, false),
		BR("br", "Brazil", FlagCoord.BR, "pt", "Portuguese", FlagCoord.PT, false),
		BS("bs", "Bahamas", FlagCoord.BS, "en", "English", FlagCoord.GB, false),
		BT("bt", "Bhutan", FlagCoord.BT, "dz", "Dzongkha", FlagCoord.BT, true),
		BW("bw", "Botswana", FlagCoord.BW, "en", "English", FlagCoord.GB, false),
		BY("by", "Belarus", FlagCoord.BY, "be", "Belarusian", FlagCoord.BY, true),
		BZ("bz", "Belize", FlagCoord.BZ, "en", "English", FlagCoord.GB, false),
		CA("ca", "Canada", FlagCoord.CA, "en", "English", FlagCoord.GB, false),
		CD("cd", "Congo (Democratic Republic of the)", FlagCoord.CD, "fr", "French", FlagCoord.FR, false),
		CF("cf", "Central African Republic", FlagCoord.CF, "fr", "French", FlagCoord.FR, false),
		CG("cg", "Congo", FlagCoord.CG, "fr", "French", FlagCoord.FR, false),
		CH("ch", "Switzerland", FlagCoord.CH, "de", "German", FlagCoord.DE, false),
		CI("ci", "Côte d'Ivoire", FlagCoord.CI, "fr", "French", FlagCoord.FR, false),
		CK("ck", "Cook Islands", FlagCoord.CK, "en", "English", FlagCoord.GB, false),
		CL("cl", "Chile", FlagCoord.CL, "es", "Spanish", FlagCoord.ES, false),
		CM("cm", "Cameroon", FlagCoord.CM, "en", "English", FlagCoord.GB, false),
		CN("cn", "China", FlagCoord.CN, "zh", "Chinese", FlagCoord.CN, true),
		CO("co", "Colombia", FlagCoord.CO, "es", "Spanish", FlagCoord.ES, false),
		CR("cr", "Costa Rica", FlagCoord.CR, "es", "Spanish", FlagCoord.ES, false),
		CU("cu", "Cuba", FlagCoord.CU, "es", "Spanish", FlagCoord.ES, false),
		CV("cv", "Cabo Verde", FlagCoord.CV, "pt", "Portuguese", FlagCoord.PT, false),
		CY("cy", "Cyprus", FlagCoord.CY, "el", "Greek (modern)", FlagCoord.GR, false),
		CZ("cz", "Czech Republic", FlagCoord.CZ, "cs", "Czech", FlagCoord.CZ, true),
		DE("de", "Germany", FlagCoord.DE, "de", "German", FlagCoord.DE, true),
		DJ("dj", "Djibouti", FlagCoord.DJ, "fr", "French", FlagCoord.FR, false),
		DK("dk", "Denmark", FlagCoord.DK, "da", "Danish", FlagCoord.DK, true),
		DM("dm", "Dominica", FlagCoord.DM, "en", "English", FlagCoord.GB, false),
		DO("do", "Dominican Republic", FlagCoord.DO, "es", "Spanish", FlagCoord.ES, false),
		DZ("dz", "Algeria", FlagCoord.DZ, "ar", "Arabic", FlagCoord.AE, false),
		EC("ec", "Ecuador", FlagCoord.EC, "es", "Spanish", FlagCoord.ES, false),
		EE("ee", "Estonia", FlagCoord.EE, "et", "Estonian", FlagCoord.EE, true),
		EG("eg", "Egypt", FlagCoord.EG, "ar", "Arabic", FlagCoord.AE, false),
		EH("eh", "Western Sahara", FlagCoord.EH, "ar", "Arabic", FlagCoord.AE, false),
		ER("er", "Eritrea", FlagCoord.ER, "aa", "Afar", FlagCoord.ER, true),
		ES("es", "Spain", FlagCoord.ES, "es", "Spanish", FlagCoord.ES, true),
		ET("et", "Ethiopia", FlagCoord.ET, "am", "Amharic", FlagCoord.ET, true),
		FI("fi", "Finland", FlagCoord.FI, "fi", "Finnish", FlagCoord.FI, true),
		FJ("fj", "Fiji", FlagCoord.FJ, "en", "English", FlagCoord.GB, false),
		FM("fm", "Micronesia (Federated States of)", FlagCoord.FM, "en", "English", FlagCoord.GB, false),
		FO("fo", "Faroe Islands", FlagCoord.FO, "fo", "Faroese", FlagCoord.FO, true),
		FR("fr", "France", FlagCoord.FR, "fr", "French", FlagCoord.FR, true),
		GA("ga", "Gabon", FlagCoord.GA, "fr", "French", FlagCoord.FR, false),
		GB("gb", "United Kingdom of Great Britain and Northern Ireland", FlagCoord.GB, "en", "English", FlagCoord.GB, true),
		GD("gd", "Grenada", FlagCoord.GD, "en", "English", FlagCoord.GB, false),
		GE("ge", "Georgia", FlagCoord.GE, "ka", "Georgian", FlagCoord.GE, true),
		GG("gg", "Guernsey", FlagCoord.GG, "en", "English", FlagCoord.GB, false),
		GH("gh", "Ghana", FlagCoord.GH, "en", "English", FlagCoord.GB, false),
		GI("gi", "Gibraltar", FlagCoord.GI, "en", "English", FlagCoord.GB, false),
		GL("gl", "Greenland", FlagCoord.GL, "kl", "Kalaallisut, Greenlandic", FlagCoord.GL, true),
		GM("gm", "Gambia", FlagCoord.GM, "en", "English", FlagCoord.GB, false),
		GN("gn", "Guinea", FlagCoord.GN, "fr", "French", FlagCoord.FR, false),
		GP("gp", "Guadeloupe", FlagCoord.GP, "fr", "French", FlagCoord.FR, false),
		GQ("gq", "Equatorial Guinea", FlagCoord.GQ, "es", "Spanish", FlagCoord.ES, false),
		GR("gr", "Greece", FlagCoord.GR, "el", "Greek (modern)", FlagCoord.GR, true),
		GT("gt", "Guatemala", FlagCoord.GT, "es", "Spanish", FlagCoord.ES, false),
		GU("gu", "Guam", FlagCoord.GU, "en", "English", FlagCoord.GB, false),
		GW("gw", "Guinea-Bissau", FlagCoord.GW, "pt", "Portuguese", FlagCoord.PT, false),
		GY("gy", "Guyana", FlagCoord.GY, "en", "English", FlagCoord.GB, false),
		HK("hk", "Hong Kong", FlagCoord.HK, "zh", "Chinese", FlagCoord.CN, false),
		HN("hn", "Honduras", FlagCoord.HN, "es", "Spanish", FlagCoord.ES, false),
		HR("hr", "Croatia", FlagCoord.HR, "hr", "Croatian", FlagCoord.HR, true),
		HT("ht", "Haiti", FlagCoord.HT, "ht", "Haitian, Haitian Creole", FlagCoord.HT, true),
		HU("hu", "Hungary", FlagCoord.HU, "hu", "Hungarian", FlagCoord.HU, true),
		ID("id", "Indonesia", FlagCoord.ID, "id", "Indonesian", FlagCoord.ID, true),
		IE("ie", "Ireland", FlagCoord.IE, "en", "English", FlagCoord.GB, false),
		IL("il", "Israel", FlagCoord.IL, "he", "Hebrew (modern)", FlagCoord.IL, true),
		IM("im", "Isle of Man", FlagCoord.IM, "en", "English", FlagCoord.GB, false),
		IN("in", "India", FlagCoord.IN, "en", "English", FlagCoord.GB, false),
		IQ("iq", "Iraq", FlagCoord.IQ, "ar", "Arabic", FlagCoord.AE, false),
		IR("ir", "Iran (Islamic Republic of)", FlagCoord.IR, "fa", "Farsi", FlagCoord.AF, false),
		IS("is", "Iceland", FlagCoord.IS, "is", "Icelandic", FlagCoord.IS, true),
		IT("it", "Italy", FlagCoord.IT, "it", "Italian", FlagCoord.IT, true),
		JE("je", "Jersey", FlagCoord.JE, "en", "English", FlagCoord.GB, false),
		JM("jm", "Jamaica", FlagCoord.JM, "en", "English", FlagCoord.GB, false),
		JO("jo", "Jordan", FlagCoord.JO, "ar", "Arabic", FlagCoord.AE, false),
		JP("jp", "Japan", FlagCoord.JP, "ja", "Japanese", FlagCoord.JP, true),
		KE("ke", "Kenya", FlagCoord.KE, "en", "English", FlagCoord.GB, false),
		KG("kg", "Kyrgyzstan", FlagCoord.KG, "ky", "Kyrgyz", FlagCoord.KG, true),
		KH("kh", "Cambodia", FlagCoord.KH, "km", "Khmer", FlagCoord.KH, true),
		KI("ki", "Kiribati", FlagCoord.KI, "en", "English", FlagCoord.GB, false),
		KM("km", "Comoros", FlagCoord.KM, "ar", "Arabic", FlagCoord.AE, false),
		KN("kn", "Saint Kitts and Nevis", FlagCoord.KN, "en", "English", FlagCoord.GB, false),
		KP("kp", "Korea (Democratic People's Republic of)", FlagCoord.KP, "ko", "Korean", FlagCoord.KR, false),
		KR("kr", "Korea (Republic of)", FlagCoord.KR, "ko", "Korean", FlagCoord.KR, true),
		KW("kw", "Kuwait", FlagCoord.KW, "ar", "Arabic", FlagCoord.AE, false),
		KY("ky", "Cayman Islands", FlagCoord.KY, "en", "English", FlagCoord.GB, false),
		KZ("kz", "Kazakhstan", FlagCoord.KZ, "kk", "Kazakh", FlagCoord.KZ, true),
		LA("la", "Lao People's Democratic Republic", FlagCoord.LA, "lo", "Lao", FlagCoord.LA, true),
		LB("lb", "Lebanon", FlagCoord.LB, "ar", "Arabic", FlagCoord.AE, false),
		LC("lc", "Saint Lucia", FlagCoord.LC, "en", "English", FlagCoord.GB, false),
		LK("lk", "Sri Lanka", FlagCoord.LK, "si", "Sinhala, Sinhalese", FlagCoord.LK, true),
		LR("lr", "Liberia", FlagCoord.LR, "en", "English", FlagCoord.GB, false),
		LS("ls", "Lesotho", FlagCoord.LS, "en", "English", FlagCoord.GB, false),
		LT("lt", "Lithuania", FlagCoord.LT, "lt", "Lithuanian", FlagCoord.LT, true),
		LU("lu", "Luxembourg", FlagCoord.LU, "lb", "Luxembourgish, Letzeburgesch", FlagCoord.LU, true),
		LV("lv", "Latvia", FlagCoord.LV, "lv", "Latvian", FlagCoord.LV, true),
		LY("ly", "Libya", FlagCoord.LY, "ar", "Arabic", FlagCoord.AE, false),
		MA("ma", "Morocco", FlagCoord.MA, "ar", "Arabic", FlagCoord.AE, false),
		MC("mc", "Monaco", FlagCoord.MC, "fr", "French", FlagCoord.FR, false),
		MD("md", "Moldova (Republic of)", FlagCoord.MD, "ro", "Romanian", FlagCoord.RO, true),
		ME("me", "Montenegro", FlagCoord.ME, "sr", "Serbian", FlagCoord.ME, true),
		MG("mg", "Madagascar", FlagCoord.MG, "fr", "French", FlagCoord.FR, false),
		MH("mh", "Marshall Islands", FlagCoord.MH, "mh", "Marshallese", FlagCoord.MH, true),
		MK("mk", "Macedonia (the former Yugoslav Republic of)", FlagCoord.MK, "mk", "Macedonian", FlagCoord.MK, true),
		ML("ml", "Mali", FlagCoord.ML, "fr", "French", FlagCoord.FR, false),
		MM("mm", "Myanmar", FlagCoord.MM, "my", "Burmese", FlagCoord.MM, true),
		MN("mn", "Mongolia", FlagCoord.MN, "mn", "Mongolian", FlagCoord.MN, true),
		MO("mo", "Macao", FlagCoord.MO, "zh", "Chinese", FlagCoord.CN, false),
		MQ("mq", "Martinique", FlagCoord.MQ, "fr", "French", FlagCoord.FR, false),
		MR("mr", "Mauritania", FlagCoord.MR, "ar", "Arabic", FlagCoord.AE, false),
		MS("ms", "Montserrat", FlagCoord.MS, "en", "English", FlagCoord.GB, false),
		MT("mt", "Malta", FlagCoord.MT, "mt", "Maltese", FlagCoord.MT, true),
		MU("mu", "Mauritius", FlagCoord.MU, "en", "English", FlagCoord.GB, false),
		MV("mv", "Maldives", FlagCoord.MV, "dv", "Divehi, Dhivehi, Maldivian", FlagCoord.MV, true),
		MW("mw", "Malawi", FlagCoord.MW, "ny", "Chichewa, Chewa, Nyanja", FlagCoord.MW, true),
		MX("mx", "Mexico", FlagCoord.MX, "es", "Spanish", FlagCoord.ES, false),
		MY("my", "Malaysia", FlagCoord.MY, "ms", "Malay", FlagCoord.MY, true),
		MZ("mz", "Mozambique", FlagCoord.MZ, "pt", "Portuguese", FlagCoord.PT, false),
		NA("na", "Namibia", FlagCoord.NA, "en", "English", FlagCoord.GB, false),
		NC("nc", "New Caledonia", FlagCoord.NC, "fr", "French", FlagCoord.FR, false),
		NE("ne", "Niger", FlagCoord.NE, "fr", "French", FlagCoord.FR, false),
		NG("ng", "Nigeria", FlagCoord.NG, "en", "English", FlagCoord.GB, false),
		NI("ni", "Nicaragua", FlagCoord.NI, "es", "Spanish", FlagCoord.ES, false),
		NL("nl", "Netherlands", FlagCoord.NL, "nl", "Dutch", FlagCoord.NL, true),
		NO("no", "Norway", FlagCoord.NO, "no", "Norwegian", FlagCoord.NO, true),
		NP("np", "Nepal", FlagCoord.NP, "ne", "Nepali", FlagCoord.NP, true),
		NR("nr", "Nauru", FlagCoord.NR, "na", "Nauru", FlagCoord.NR, true),
		NZ("nz", "New Zealand", FlagCoord.NZ, "en", "English", FlagCoord.GB, false),
		OM("om", "Oman", FlagCoord.OM, "ar", "Arabic", FlagCoord.AE, false),
		PA("pa", "Panama", FlagCoord.PA, "es", "Spanish", FlagCoord.ES, false),
		PE("pe", "Peru", FlagCoord.PE, "es", "Spanish", FlagCoord.ES, false),
		PF("pf", "French Polynesia", FlagCoord.PF, "fr", "French", FlagCoord.FR, false),
		PG("pg", "Papua New Guinea", FlagCoord.PG, "en", "English", FlagCoord.GB, false),
		PH("ph", "Philippines", FlagCoord.PH, "tl", "Tagalog", FlagCoord.PH, true),
		PK("pk", "Pakistan", FlagCoord.PK, "ur", "Urdu", FlagCoord.PK, true),
		PL("pl", "Poland", FlagCoord.PL, "pl", "Polish", FlagCoord.PL, true),
		PR("pr", "Puerto Rico", FlagCoord.PR, "en", "English", FlagCoord.GB, false),
		PS("ps", "Palestine, State of", FlagCoord.PS, "ar", "Arabic", FlagCoord.AE, false),
		PT("pt", "Portugal", FlagCoord.PT, "pt", "Portuguese", FlagCoord.PT, true),
		PW("pw", "Palau", FlagCoord.PW, "pau", "Palauan", FlagCoord.PW, true),
		PY("py", "Paraguay", FlagCoord.PY, "es", "Spanish", FlagCoord.ES, false),
		QA("qa", "Qatar", FlagCoord.QA, "ar", "Arabic", FlagCoord.AE, false),
		RE("re", "Réunion", FlagCoord.RE, "fr", "French", FlagCoord.FR, false),
		RO("ro", "Romania", FlagCoord.RO, "ro", "Romanian", FlagCoord.RO, true),
		RS("rs", "Serbia", FlagCoord.RS, "sr", "Serbian", FlagCoord.RS, true),
		RU("ru", "Russian Federation", FlagCoord.RU, "ru", "Russian", FlagCoord.RU, true),
		RW("rw", "Rwanda", FlagCoord.RW, "rw", "Kinyarwanda", FlagCoord.RW, true),
		SA("sa", "Saudi Arabia", FlagCoord.SA, "ar", "Arabic", FlagCoord.AE, false),
		SB("sb", "Solomon Islands", FlagCoord.SB, "en", "English", FlagCoord.GB, false),
		SC("sc", "Seychelles", FlagCoord.SC, "en", "English", FlagCoord.GB, false),
		SD("sd", "Sudan", FlagCoord.SD, "ar", "Arabic", FlagCoord.AE, false),
		SE("se", "Sweden", FlagCoord.SE, "sv", "Swedish", FlagCoord.SE, true),
		SG("sg", "Singapore", FlagCoord.SG, "cmn", "Mandarin Chinese", FlagCoord.SG, true),
		SI("si", "Slovenia", FlagCoord.SI, "sl", "Slovene", FlagCoord.SI, true),
		SK("sk", "Slovakia", FlagCoord.SK, "sk", "Slovak", FlagCoord.SK, true),
		SL("sl", "Sierra Leone", FlagCoord.SL, "en", "English", FlagCoord.GB, false),
		SM("sm", "San Marino", FlagCoord.SM, "it", "Italian", FlagCoord.IT, false),
		SN("sn", "Senegal", FlagCoord.SN, "fr", "French", FlagCoord.FR, false),
		SO("so", "Somalia", FlagCoord.SO, "so", "Somali", FlagCoord.SO, true),
		SR("sr", "Suriname", FlagCoord.SR, "nl", "Dutch", FlagCoord.NL, false),
		ST("st", "Sao Tome and Principe", FlagCoord.ST, "pt", "Portuguese", FlagCoord.PT, false),
		SV("sv", "El Salvador", FlagCoord.SV, "es", "Spanish", FlagCoord.ES, false),
		SY("sy", "Syrian Arab Republic", FlagCoord.SY, "ar", "Arabic", FlagCoord.AE, false),
		SZ("sz", "Swaziland", FlagCoord.SZ, "en", "English", FlagCoord.GB, false),
		TC("tc", "Turks and Caicos Islands", FlagCoord.TC, "en", "English", FlagCoord.GB, false),
		TD("td", "Chad", FlagCoord.TD, "fr", "French", FlagCoord.FR, false),
		TG("tg", "Togo", FlagCoord.TG, "fr", "French", FlagCoord.FR, false),
		TH("th", "Thailand", FlagCoord.TH, "th", "Thai", FlagCoord.TH, true),
		TJ("tj", "Tajikistan", FlagCoord.TJ, "tg", "Tajik", FlagCoord.TJ, true),
		TL("tl", "Timor-Leste", FlagCoord.TL, "tet", "Tetum", FlagCoord.TL, true),
		TM("tm", "Turkmenistan", FlagCoord.TM, "tk", "Turkmen", FlagCoord.TM, true),
		TN("tn", "Tunisia", FlagCoord.TN, "ar", "Arabic", FlagCoord.AE, false),
		TO("to", "Tonga", FlagCoord.TO, "to", "Tonga (Tonga Islands)", FlagCoord.TO, true),
		TR("tr", "Turkey", FlagCoord.TR, "tr", "Turkish", FlagCoord.TR, true),
		TT("tt", "Trinidad and Tobago", FlagCoord.TT, "en", "English", FlagCoord.GB, false),
		TV("tv", "Tuvalu", FlagCoord.TV, "tvl", "Tuvaluan", FlagCoord.TV, true),
		TW("tw", "Taiwan, Province of China", FlagCoord.TW, "zh", "Chinese", FlagCoord.CN, false),
		TZ("tz", "Tanzania, United Republic of", FlagCoord.TZ, "sw", "Swahili", FlagCoord.TZ, true),
		UA("ua", "Ukraine", FlagCoord.UA, "uk", "Ukrainian", FlagCoord.UA, true),
		UG("ug", "Uganda", FlagCoord.UG, "en", "English", FlagCoord.GB, false),
		US("us", "United States of America", FlagCoord.US, "en", "English", FlagCoord.GB, false),
		UY("uy", "Uruguay", FlagCoord.UY, "es", "Spanish", FlagCoord.ES, false),
		UZ("uz", "Uzbekistan", FlagCoord.UZ, "uz", "Uzbek", FlagCoord.UZ, true),
		VA("va", "Holy See", FlagCoord.VA, "la", "Latin", FlagCoord.VA, true),
		VC("vc", "Saint Vincent and the Grenadines", FlagCoord.VC, "en", "English", FlagCoord.GB, false),
		VE("ve", "Venezuela (Bolivarian Republic of)", FlagCoord.VE, "es", "Spanish", FlagCoord.ES, false),
		VG("vg", "Virgin Islands (British)", FlagCoord.VG, "en", "English", FlagCoord.GB, false),
		VI("vi", "Virgin Islands (U.S.)", FlagCoord.VI, "en", "English", FlagCoord.GB, false),
		VN("vn", "Viet Nam", FlagCoord.VN, "vi", "Vietnamese", FlagCoord.VN, true),
		VU("vu", "Vanuatu", FlagCoord.VU, "bi", "Bislama", FlagCoord.VU, true),
		WS("ws", "Samoa", FlagCoord.WS, "sm", "Samoan", FlagCoord.WS, true),
		YE("ye", "Yemen", FlagCoord.YE, "ar", "Arabic", FlagCoord.AE, false),
		ZA("za", "South Africa", FlagCoord.ZA, "zu", "Zulu", FlagCoord.ZA, true),
		ZM("zm", "Zambia", FlagCoord.ZM, "en", "English", FlagCoord.GB, false),
		ZW("zw", "Zimbabwe", FlagCoord.ZW, "en", "English", FlagCoord.GB, false);

		private final String countryCode;
		private final String country;
		private final FlagCoord countryFlag;
		private final String languageCode;
		private final String language;
		private final FlagCoord languageFlag;
		private final boolean mainLanguage;

		private LanguageCountryData(String countryCode, String country, FlagCoord countryFlag,
									String languageCode, String language, FlagCoord languageFlag,
									boolean mainLanguage) {
			this.countryCode = countryCode;
			this.country = country;
			this.countryFlag = countryFlag;
			this.languageCode = languageCode;
			this.language = language;
			this.languageFlag = languageFlag;
			this.mainLanguage = mainLanguage;
		}

	}

	public static final int FLAG_HEIGHT = 16;
	private static final int FLAG_REAL_WIDTH = 32;
	private static final int FLAG_REAL_HEIGHT = 24;
	
	private static EnhancedLocale emptyLocale;
	private static Map<String, EnhancedLocale> byCountry;
	private static Map<String, EnhancedLocale> byLanguage;

	public static void init() throws Exception {
		ImageIcon transparentFlag = getTransparentFlag();
		emptyLocale = new EnhancedLocale("_", "", transparentFlag, "_", "", transparentFlag);
		
		BufferedImage flags = ImageIO.read(EnhancedLocaleMap.class.getResource("/images/flags.png"));
		EnumMap<FlagCoord, ImageIcon> flagMap = new EnumMap<>(FlagCoord.class);
		for (FlagCoord flagCoord : FlagCoord.values()) {
			flagMap.put(flagCoord, getFlag(flags, flagCoord));
		}

		if (byCountry == null) {
			byCountry = new HashMap<>();
		}
		if (byLanguage == null) {
			byLanguage = new HashMap<>();
		}

		byCountry.clear();
		byLanguage.clear();

		for (LanguageCountryData data : LanguageCountryData.values()) {
			EnhancedLocale enhancedLocale = new EnhancedLocale(data.countryCode, data.country, flagMap.get(data.countryFlag),
															   data.languageCode, data.language, flagMap.get(data.languageFlag));
			byCountry.put(data.countryCode, enhancedLocale);
			if (data.mainLanguage) {
				byLanguage.put(data.languageCode, enhancedLocale);
			}
		}
	}

	public static EnhancedLocale getEmptyLocale() {
		return emptyLocale;
	}
	
	public static boolean isEmptyLocale(EnhancedLocale emptyLocale) {
		return emptyLocale.equals(EnhancedLocaleMap.emptyLocale);
	}
	
	public static EnhancedLocale getByCountry(String country) {
		return byCountry.get(country.toLowerCase());
	}

	public static EnhancedLocale getByLanguage(String language) {
		return byLanguage.get(language.toLowerCase());
	}
	
	public static Set<EnhancedLocale> getLanguages() {
		Set<EnhancedLocale> result = new TreeSet<>(EnhancedLocale.languageComparator());
		
		result.addAll(byLanguage.values());
		
		return result;
	}
	
	private static ImageIcon getFlag(BufferedImage flags, FlagCoord flagCoord) {
		ImageIcon result = new ImageIcon(flags.getSubimage(flagCoord.x, flagCoord.y, FLAG_REAL_WIDTH, FLAG_REAL_HEIGHT));
		
		double scaleFactor = (double)FLAG_HEIGHT / (double)result.getIconHeight();
		double width = scaleFactor * (double)result.getIconWidth();

		if (scaleFactor != 0d) {
			result = new ImageIcon(result.getImage().getScaledInstance((int)width, FLAG_HEIGHT, Image.SCALE_SMOOTH));
		}
		
		return result;
	}
	
	private static ImageIcon getTransparentFlag() {
		ImageIcon result = null;
		
		double scaleFactor = (double)FLAG_HEIGHT / (double)FLAG_REAL_HEIGHT;
		double width = scaleFactor * (double)FLAG_REAL_WIDTH;
		
		BufferedImage image = new BufferedImage((int)width, (int)FLAG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		((Graphics2D)image.getGraphics()).setBackground(new Color(0, 0, 0, 0));
		
		result = new ImageIcon(image);
		
		return result;
	}

	private EnhancedLocaleMap() {
	}

}
