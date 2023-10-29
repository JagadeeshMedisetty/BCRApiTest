Feature: User registers with Buggy Rating App

  @SmokeTest
  Scenario Outline: User Registers with Buggy Cars Rating App with Valid details
    Given I register with Buggy Rating App with "<Login>", "<FirstName>", "<LastName>", "<Password>"
    When I login with the credentials
    Then I should get a valid token in response
  Examples:
    | Login    | FirstName  | LastName   | Password       |
    | rjohn    | Richard    | John       | Test@123       |
    | vkohli   | Virat      | Kohli      | MyP@$$w0rd     |
    | rSharma  | Rohit      | Sharma     | Test@123       |

