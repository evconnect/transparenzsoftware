package com.hastobe.transparenzsoftware.verification.xml;

import com.hastobe.transparenzsoftware.verification.DecodingException;
import com.hastobe.transparenzsoftware.verification.EncodingDecoder;
import com.hastobe.transparenzsoftware.verification.EncodingType;
import com.hastobe.transparenzsoftware.verification.input.InvalidInputException;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "publicKey")
@XmlAccessorType(XmlAccessType.FIELD)
public class PublicKey {

    @XmlAttribute
    private String encoding;

    @XmlValue
    private String value;

    public PublicKey() {
    }

    public PublicKey(EncodingType encoding, String value) {
        this.encoding = encoding.getCode();
        this.value = value;
    }

    public String getEncoding() {
        return encoding != null ? encoding.trim() : null;
    }

    public EncodingType getEncodingType() {
        return EncodingType.fromCode(getEncoding());
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getValue() {
        return value != null ? value.trim() : null;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void validate(boolean enforceTypeChecking) throws InvalidInputException {
        if (this.value == null || value.trim().isEmpty()) {
            throw new InvalidInputException("Empty value provided", "error.values.publickey.empty.value");
        }
        if (enforceTypeChecking) {
            if (EncodingType.fromCode(encoding) == null) {
                throw new InvalidInputException("Invalid encoding type supplied", "error.values.publickey.invalid.format");
            }
            try {
                EncodingDecoder.decodePublicKey(this);
            } catch (DecodingException e) {
                throw new InvalidInputException("Cannot encode data", "error.values.publickey.cannot.encode");
            }
        }
    }
}
