<h1>REST API Testing Training</h1>
<p>This is the project I developed as part of my REST API testing training to become a Java QA Automation Engineer within the company.</p>

<h2>Technology Stack:</h2>
<ul>
    <li>Programming language - Java</li>
    <li>Build and project management tool - Maven</li>
    <li>Testing framework - JUnit 5</li>
    <li>Request handling - Apache HTTP Client & Rest Assured</li>
    <li>Reporting framework - Allure</li>
    <li>Integration - Docker</li>
</ul>

<h2>Overview</h2>
<p>During the training, we will test the provided web-service from a Docker container. All requirements for the web-service must work properly only for one-thread execution. The web-service handles the storage of users and their information: name, age, sex, and zip code.</p>
<p>Application provides:</p>
<ul>
    <li>Information about all stored users;</li>
    <li>Possibility to create, update, delete users;</li>
    <li>Information about available zip codes;</li>
    <li>Possibility to add new zip codes.</li>
</ul>

<h2>Task 1 - Authentication:</h2>
<ol>
    <li>Go to <a href="http://localhost:<port>/swagger-ui/">http://localhost:<port>/swagger-ui/</a> and read information about all endpoints.</li>
    <li>Create Maven project with the dependencies needed.</li>
    <li>Develop client code to get bearer tokens with read and write scopes separately.</li>
    <li>No tests should be developed for this task</li>
</ol>

<h3>Requirements:</h3>
<ul>
    <li><strong>Scenario #1:</strong>
        <ul>
            <li>When I send a POST request to <code>/oauth/token</code></li>
            <li>And I put parameters <code>grant_type=client_credentials</code> and <code>scope=write</code></li>
            <li>And I set username and password for basic auth</li>
            <li>Then I get a response with a bearer token which works for any POST, PUT, PATCH, DELETE methods of web-service</li>
        </ul>
    </li>
    <li><strong>Scenario #2:</strong>
        <ul>
            <li>When I send a POST request to <code>/oauth/token</code></li>
            <li>And I put parameters <code>grant_type=client_credentials</code> and <code>scope=read</code></li>
            <li>And I set username and password for basic auth</li>
            <li>Then I get a response with a bearer token which works for any GET methods of web-service</li>
        </ul>
    </li>
</ul>

<h2>Task 2 - Zip Codes:</h2>
<p>Write tests to cover requirements for zip codes functionality</p>

<h3>Requirements:</h3>
<ul>
    <li><strong>Scenario #1:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a GET request to the <code>/zip-codes</code> endpoint</li>
            <li>Then I get a 200 response code</li>
            <li>And I get all available zip codes in the application for now</li>
        </ul>
    </li>
    <li><strong>Scenario #2:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a POST request to the <code>/zip-codes/expand</code> endpoint</li>
            <li>And the request body contains a list of zip codes</li>
            <li>Then I get a 201 response code</li>
            <li>And zip codes from the request body are added to the available zip codes of the application</li>
        </ul>
    </li>
    <li><strong>Scenario #3:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a POST request to the <code>/zip-codes/expand</code> endpoint</li>
            <li>And the request body contains a list of zip codes</li>
            <li>And the list of zip codes has duplications for available zip codes</li>
            <li>Then I get a 201 response code</li>
            <li>And zip codes from the request body are added to the available zip codes of the application without duplicates</li>
        </ul>
    </li>
    <li><strong>Scenario #4:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a POST request to the <code>/zip-codes/expand</code> endpoint</li>
            <li>And the request body contains a list of zip codes</li>
            <li>And the list of zip codes has duplications of already used zip codes</li>
            <li>Then I get a 201 response code</li>
            <li>And zip codes from the request body are added to the available zip codes of the application without duplicates</li>
        </ul>
    </li>
</ul>

<h2>Task 3 - Create Users:</h2>
<p>Write tests to cover requirements for user creation functionality</p>

<h3>Requirements:</h3>
<ul>
    <li><strong>Scenario #1:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a POST request to the <code>/users</code> endpoint</li>
            <li>And the request body contains a user to add</li>
            <li>And all fields are filled in</li>
            <li>Then I get a 201 response code</li>
            <li>And the user is added to the application</li>
            <li>And the zip code is removed from the available zip codes of the application</li>
        </ul>
    </li>
    <li><strong>Scenario #2:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a POST request to the <code>/users</code> endpoint</li>
            <li>And the request body contains a user to add</li>
            <li>And only required fields are filled in</li>
            <li>Then I get a 201 response code</li>
            <li>And the user is added to the application</li>
        </ul>
    </li>
    <li><strong>Scenario #3:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a POST request to the <code>/users</code> endpoint</li>
            <li>And the request body contains a user to add</li>
            <li>And all fields are filled in</li>
            <li>And the zip code is incorrect (unavailable)</li>
            <li>Then I get a 424 response code</li>
            <li>And the user is NOT added to the application</li>
        </ul>
    </li>
    <li><strong>Scenario #4:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a POST request to the <code>/users</code> endpoint</li>
            <li>And the request body contains a user to add with the same name and sex as an existing user</li>
            <li>Then I get a 400 response code</li>
            <li>And the user is NOT added to the application</li>
        </ul>
    </li>
</ul>

<h2>Task 4 - Filter Users:</h2>
<p>Write tests to cover requirements for user filtering</p>

<h3>Requirements:</h3>
<ul>
    <li><strong>Scenario #1:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a GET request to the <code>/users</code> endpoint</li>
            <li>Then I get a 200 response code</li>
            <li>And I get all users stored in the application for now</li>
        </ul>
    </li>
    <li><strong>Scenario #2:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a GET request to the <code>/users</code> endpoint</li>
            <li>And I add the <code>olderThan</code> parameter</li>
            <li>Then I get a 200 response code</li>
            <li>And I get all users older than the parameter value</li>
        </ul>
    </li>
    <li><strong>Scenario #3:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a GET request to the <code>/users</code> endpoint</li>
            <li>And I add the <code>youngerThan</code> parameter</li>
            <li>Then I get a 200 response code</li>
            <li>And I get all users younger than the parameter value</li>
        </ul>
    </li>
    <li><strong>Scenario #4:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a GET request to the <code>/users</code> endpoint</li>
            <li>And I add the <code>sex</code> parameter</li>
            <li>Then I get a 200 response code</li>
            <li>And I get all users with sex equal to the parameter value</li>
        </ul>
    </li>
</ul>

<h2>Task 5 - Update Users:</h2>
<p>Write tests to cover requirements for updating user functionality</p>

<h3>Requirements:</h3>
<ul>
    <li><strong>Scenario #1:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a PUT/PATCH request to the <code>/users</code> endpoint</li>
            <li>And the request body contains a user to update and new values</li>
            <li>Then I get a 200 response code</li>
            <li>And the user is updated</li>
        </ul>
    </li>
    <li><strong>Scenario #2:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a PUT/PATCH request to the <code>/users</code> endpoint</li>
            <li>And the request body contains a user to update and new values</li>
            <li>And the new zip code is incorrect (unavailable)</li>
            <li>Then I get a 424 response code</li>
            <li>And the user is NOT updated</li>
        </ul>
    </li>
    <li><strong>Scenario #3:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a PUT/PATCH request to the <code>/users</code> endpoint</li>
            <li>And the request body contains a user to update and new values</li>
            <li>And the required fields are not filled in</li>
            <li>Then I get a 409 response code</li>
            <li>And the user is NOT updated</li>
        </ul>
    </li>
</ul>

<h2>Task 6 - Delete Users:</h2>
<p>Write tests to cover requirements for deleting user functionality</p>

<h3>Requirements:</h3>
<ul>
    <li><strong>Scenario #1:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a DELETE request to the <code>/users</code> endpoint</li>
            <li>And the request body contains a user to delete</li>
            <li>Then I get a 204 response code</li>
            <li>And the user is deleted</li>
            <li>And its zip code is returned in the list of available zip codes</li>
        </ul>
    </li>
    <li><strong>Scenario #2:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a DELETE request to the <code>/users</code> endpoint</li>
            <li>And the request body contains a user to delete (required fields only)</li>
            <li>Then I get a 204 response code</li>
            <li>And the user is deleted</li>
            <li>And its zip code is returned in the list of available zip codes</li>
        </ul>
    </li>
    <li><strong>Scenario #3:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a DELETE request to the <code>/users</code> endpoint</li>
            <li>And the request body contains a user to delete (any required field not filled)</li>
            <li>Then I get a 409 response code</li>
            <li>And the user is deleted</li>
        </ul>
    </li>
</ul>

<h2>Task 7 - Upload Users:</h2>
<p>Write tests to cover requirements for uploading user functionality</p>

<h3>Requirements:</h3>
<ul>
    <li><strong>Scenario #1:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a POST request to the <code>/users/upload</code> endpoint</li>
            <li>And the request body contains a JSON file with an array of users to upload</li>
            <li>Then I get a 201 response code</li>
            <li>And all users are replaced with users from the file</li>
            <li>And the response contains the number of uploaded users</li>
        </ul>
    </li>
    <li><strong>Scenario #2:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a POST request to the <code>/users/upload</code> endpoint</li>
            <li>And the request body contains a JSON file with an array of users to upload</li>
            <li>And at least 1 user has an incorrect (unavailable) zip code</li>
            <li>Then I get a 424 response code</li>
            <li>And the users are NOT uploaded</li>
        </ul>
    </li>
    <li><strong>Scenario #3:</strong>
        <ul>
            <li>Given I am an authorized user</li>
            <li>When I send a POST request to the <code>/users/upload</code> endpoint</li>
            <li>And the request body contains a JSON file with an array of users to upload</li>
            <li>And at least 1 user has a required field not filled</li>
            <li>Then I get a 409 response code</li>
            <li>And the users are NOT uploaded</li>
        </ul>
    </li>
</ul>

<h2>Task 8 - Allure Reporting:</h2>
<ul>
    <li>Add the Allure Framework to the project</li>
    <li>Add payload to tests in the report if required</li>
    <li>Add <code>@Step</code> annotation for better readability of the report</li>
    <li>Mark tests with bugs with the corresponding Allure annotation</li>
</ul>

<h2>Final Task - Rest Assured:</h2>
<ul>
    <li>Pull one more Docker image (containing an improved web-service) and start the container</li>
    <li>Execute all tests in the project with Apache HTTP Client</li>
    <li>Execute all tests in the project with Rest Assured Framework</li>
    <li>Make sure ALL tests are passed</li>
</ul>
