public class Policy {

    private static int nextPolicyID = 1;
    private String policyID;
    private Quotation quotation;

    public Policy(Quotation quotation) {
        this.policyID = String.format("POL-%04d", nextPolicyID++);
        this.quotation = quotation;
    }

    public String getPolicyID() {
        return policyID;
    }

    public String getDetails() {
        return "\nPolicy ID: " + policyID +
                "\nBased on Quotation: " + quotation.getQuotationID();
    }
}