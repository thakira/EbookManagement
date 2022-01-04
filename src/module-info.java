module gp2_vorlageFX {
	exports controller;
	exports model;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
        requires javafx.fxml;
    
    opens controller to javafx.fxml; 

}