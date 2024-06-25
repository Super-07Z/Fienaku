package bancofie.com.bo.fienaku.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class fienakuNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;

    public fienakuNotFoundException(String code) {
        super("Cannot find product with ID: " + code);
    }

}
