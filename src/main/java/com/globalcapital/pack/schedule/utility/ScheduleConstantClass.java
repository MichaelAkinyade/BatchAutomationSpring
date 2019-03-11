package com.globalcapital.pack.schedule.utility;

public class ScheduleConstantClass {

	public static final int dummyBatch = 15;
	public static final int financialBatchOne = 1;
	public static final int financialBatchTwo = 2;
	public static final int autoFinanceBatchOne = 7;
	public static final int autoFinanceBatchTwo = 8;
	public static final int genericFeesBatchOne = 3;
	public static final int genericFeesBatchTwo = 4;
	public static final int coverageBatchBatchOne = 5;
	public static final int coverageBatchBatchTwo = 6;
	public static final int reschedulingBatchOne = 9;
	public static final int reschedulingBatchTwo = 10;
	public static final int issuingBatchOne = 11;
	public static final int issuingBatchTwo = 12;
	public static final int regularBatchOne = 13;
	public static final int regularBatchTwo = 14;

	public static final int acutrialWeekly = 16;
	public static final int lifeCoversWeekly = 17;
	public static final int fundSplitsWeekly = 18;
	public static final int policyBeneficiaries = 19;
	public static final int policyHolders = 20;
	public static final int policyPayers = 21;
	public static final int thirdPartyActiveAddress = 22;
	public static final int termActurialExtractCFI = 23;
	public static final int termActurialExtractDeact = 24;
	public static final int termActurialExtractDeath = 25;
	public static final int termActurialExtractRedemp = 26;
	public static final int termActurialExtractSurren = 27;
	public static final int termActurialExtractTerm = 28;

	public static String resolveBatchCodeToBatchName(int batchTypeId) {

		String retVal = "";

		switch (batchTypeId) {

		case dummyBatch:
			retVal = "dummyBatch";

			break;

		case financialBatchOne:
			retVal = "financialBatchOne";
			break;

		case financialBatchTwo:
			retVal = "financialBatchTwo";
			break;

		case autoFinanceBatchOne:
			retVal = "autoFinanceBatchOne";
			break;

		case autoFinanceBatchTwo:
			retVal = "autoFinanceBatchTwo";
			break;

		case genericFeesBatchOne:
			retVal = "genericFeesBatchOne";
			break;

		case genericFeesBatchTwo:
			retVal = "genericFeesBatchTwo";
			break;

		case coverageBatchBatchOne:
			retVal = "coverageBatchBatchOne";
			break;

		case coverageBatchBatchTwo:
			retVal = "coverageBatchBatchTwo";
			break;

		case reschedulingBatchOne:
			retVal = "reschedulingBatchOne";
			break;

		case reschedulingBatchTwo:
			retVal = "reschedulingBatch";
			break;

		case issuingBatchOne:
			retVal = "issuingBatchOne";
			break;

		case issuingBatchTwo:
			retVal = "issuingBatchTwo";
			break;

		case regularBatchOne:
			retVal = "regularBatchOne";
			break;

		case regularBatchTwo:
			retVal = "regularBatchTwo";
			break;

		case acutrialWeekly:
			retVal = "acutrialWeekly";
			break;

		case lifeCoversWeekly:
			retVal = "lifeCoversWeekly";
			break;

		case fundSplitsWeekly:
			retVal = "fundSplitsWeekly";
			break;

		case policyBeneficiaries:
			retVal = "policyBeneficiaries";
			break;
		case policyHolders:
			retVal = "policyHolders";
			break;
		case policyPayers:
			retVal = "policyPayers";
			break;
		case thirdPartyActiveAddress:
			retVal = "thirdPartyActiveAddress";
			break;
		case termActurialExtractCFI:
			retVal = "termActurialExtractCFI";
			break;
		case termActurialExtractDeact:
			retVal = "termActurialExtractDeact";
			break;
		case termActurialExtractDeath:
			retVal = "termActurialExtractDeath";
			break;

		case termActurialExtractRedemp:
			retVal = "termActurialExtractRedemp";
			break;
		case termActurialExtractSurren:
			retVal = "termActurialExtractSurren";
			break;
		case termActurialExtractTerm:
			retVal = "termActurialExtractTerm";
			break;

		default:
			break;
		}

		return retVal;
	}

}
