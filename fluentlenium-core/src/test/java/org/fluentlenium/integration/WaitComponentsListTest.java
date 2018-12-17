package org.fluentlenium.integration;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.assertEquals;

public class WaitComponentsListTest extends IntegrationFluentTest {
    private static int ROW_BEING_CHANGED = 1;
    private static final String VALUE_OF_NONREPLACED_ROW = "row2";
    private static final String VALUE_OF_REPLACED_ROW = "replaced row2";

    private static final String ADD_ROW_SCRIPT = "addRow()";
    private static final String REPLACE_ROW_SCRIPT = "replaceRow()";

    @Page
    private RowsListPage annotatedPage;

    @Test
    public void shouldInitializePageAfterCall() {
        goTo(ELEMENT_REPLACE_URL);
        executeScript(ADD_ROW_SCRIPT);
        executeScript(REPLACE_ROW_SCRIPT);
        assertEquals(annotatedPage.getComponentsList().size(), 3);
        assertEquals(annotatedPage.getComponentsList().get(ROW_BEING_CHANGED).getText(), VALUE_OF_REPLACED_ROW);
    }

    @Test
    public void shouldNewInstanceReturnUpdatedPage() {
        goTo(ELEMENT_REPLACE_URL);
        executeScript(ADD_ROW_SCRIPT);
        executeScript(REPLACE_ROW_SCRIPT);
        RowsListPage newInstancePage = newInstance(RowsListPage.class);
        assertEquals(newInstancePage.getComponentsList().size(), 3);
        assertEquals(newInstancePage.getComponentsList().get(ROW_BEING_CHANGED).getText(), VALUE_OF_REPLACED_ROW);
    }

    @Test
    public void shouldNewInstanceRequireUpdate() {
        goTo(ELEMENT_REPLACE_URL);
        RowsListPage newInstancePage = newInstance(RowsListPage.class);
        assertEquals(newInstancePage.getComponentsList().size(), 2);
        assertEquals(newInstancePage.getComponentsList().get(ROW_BEING_CHANGED).getText(), VALUE_OF_NONREPLACED_ROW);
        executeScript(ADD_ROW_SCRIPT);
        executeScript(REPLACE_ROW_SCRIPT);

        RowsListPage updatedPage = newInstancePage.reset();
        assertEquals(updatedPage.getComponentsList().size(), 3);
        assertEquals(updatedPage.getComponentsList().get(ROW_BEING_CHANGED).getText(), VALUE_OF_REPLACED_ROW);
    }

    @Test
    public void shouldRequireReinitializationWhenAccessedFirst() {
        goTo(ELEMENT_REPLACE_URL);
        assertEquals(annotatedPage.getComponentsList().size(), 2);
        assertEquals(annotatedPage.getComponentsList().get(ROW_BEING_CHANGED).getText(), VALUE_OF_NONREPLACED_ROW);

        executeScript(ADD_ROW_SCRIPT);
        executeScript(REPLACE_ROW_SCRIPT);
        RowsListPage updatedPage = annotatedPage.reset();
        assertEquals(updatedPage.getComponentsList().size(), 3);
        assertEquals(updatedPage.getComponentsList().get(ROW_BEING_CHANGED).getText(), VALUE_OF_REPLACED_ROW);
    }

}

class RowsListPage extends FluentPage {
    @FindBy(css = ".row")
    private FluentList<RowComponent> componentsList;

    FluentList<RowComponent> getComponentsList() {
        return componentsList;
    }
}

class RowComponent extends FluentWebElement {
    @FindBy(css = ".inner-row")
    private FluentWebElement innerTag;

    RowComponent(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
        super(element, control, instantiator);
    }

    String getText() {
        return innerTag.text();
    }
}
