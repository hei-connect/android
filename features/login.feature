Feature: Login feature
    In order to use the app
    As a valid user (already created on the website)
    I want to be able to authenticate

  Scenario: As a valid user I can log into the app
    When I press "Login"
    Then I see "Welcome to coolest app ever"
