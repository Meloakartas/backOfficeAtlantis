package backoffice.model;

public class Key {

    private String modulus;
    private String exponent;

    public String getModulus() {
        return modulus;
    }

    public void setModulus(String modulus) {
        this.modulus = modulus;
    }

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    public Key(String modulus, String exponent)
    {
        this.modulus = modulus;
        this.exponent = exponent;
    }
}