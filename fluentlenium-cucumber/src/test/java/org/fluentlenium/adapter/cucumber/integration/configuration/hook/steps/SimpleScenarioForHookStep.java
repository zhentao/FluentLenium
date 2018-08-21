package org.fluentlenium.adapter.cucumber.integration.configuration.hook.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.adapter.cucumber.integration.configuration.hook.page.LocalWithHookPage;
import org.fluentlenium.adapter.cucumber.integration.configuration.hook.page.LocalWithHookPage2;
import org.fluentlenium.core.annotation.Page;

public class SimpleScenarioForHookStep {

    @Page
    private LocalWithHookPage page;

    @Page
    private LocalWithHookPage2 page2;

    @Given(value = "scenario I am on the first page")
    public void step1() {
        page.go();
    }

    @When(value = "scenario I click on next page")
    public void step2() {
        page.clickLink();
    }

    @Then(value = "scenario I am on the second page")
    public void step3() {
        page2.isAt();
    }
}