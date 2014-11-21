package org.vaadin.alump.columnlayout.material;

import com.vaadin.shared.Connector;
import com.vaadin.ui.Component;
import org.vaadin.alump.columnlayout.ColumnLayout;
import org.vaadin.alump.columnlayout.client.share.MaterialColumnLayoutState;

import java.util.ArrayList;
import java.util.List;

/**
 * Example extension of ColumnLayout that provides Material design changes to components by modifying the slot content
 * on client side.
 * @see org.vaadin.alump.columnlayout.notooltip
 */
public class MaterialColumnLayout extends ColumnLayout {

    public final static String STYLE_NAME = "material";

    /**
     * Stylename that can be used with some field types (eg. ComboBox) inside this layout to indicate options are
     * still loaded, and user should wait until options are available
     */
    public final static String LOADING_FIELD_STYLE_NAME = "loading-field";

    public MaterialColumnLayout() {
        addStyleName(STYLE_NAME);
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);

        // Clean up unit map
        List<Connector> removed = new ArrayList<Connector>();
        for(Connector unitMapChild : getState().unitMap.keySet()) {
            if(((Component)unitMapChild).getParent() != this) {
                removed.add((Component)unitMapChild);
            }
        }
        for(Connector remove : removed) {
            getState().unitMap.remove(remove);
        }
    }

    /**
     * Define unit for component. Works best with NoTooltipTextFields.
     * @param child Child component that should have units shown
     * @param unit Unit text (eg. "dollars")
     */
    public void setComponentUnit(Component child, String unit) {
        if(child.getParent() != this) {
            throw new IllegalArgumentException("Given component is not child of this layout");
        }

        if(unit != null) {
            getState().unitMap.put(child, unit);
        } else {
            getState().unitMap.remove(child);
        }
    }

    @Override
    protected MaterialColumnLayoutState getState() {
        return (MaterialColumnLayoutState)super.getState();
    }
}
