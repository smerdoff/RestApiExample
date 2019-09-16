package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class SingleUser {

    @Expose
    User data;
}
