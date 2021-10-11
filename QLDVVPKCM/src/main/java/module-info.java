module com.doannganh.qldvvpkcm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.base;

    opens com.doannganh.qldvvpkcm to javafx.fxml;
    exports com.doannganh.qldvvpkcm;
    exports com.doannganh.pojo;
}
