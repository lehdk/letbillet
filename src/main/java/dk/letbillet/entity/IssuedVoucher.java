package dk.letbillet.entity;

public class IssuedVoucher {

    private int id;
    private String Guid;
    private VoucherType voucherType;

    public IssuedVoucher(int id, String guid, VoucherType voucherType) {
        this.id = id;
        Guid = guid;
        this.voucherType = voucherType;
    }

    public int getId() {
        return id;
    }

    public String getGuid() {
        return Guid;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
