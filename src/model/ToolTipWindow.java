package model;

import javafx.scene.control.Tooltip;

/**
 * ToolTip-Klasse.
 * 
 * @author Verena Kauth
 *
 */
public class ToolTipWindow {

    /**
     * Methode zum Erstellen eines ToolTip-Events.
     * 
     * @param message
     * @return tooltip
     */
    @SuppressWarnings("exports")
    public static Tooltip createToolTip(String message) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(message);
        tooltip.setGraphicTextGap(0);
        return tooltip;

    }
}
