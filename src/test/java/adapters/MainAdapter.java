package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainAdapter {

    Gson gson;
    public MainAdapter() {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

}
