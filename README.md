
# Ratifier
Ratifier is a form validation library for Android.
![alt tag](https://raw.githubusercontent.com/Hamadakram/Ratifier/master/app/logo.jpeg)
![](https://github.com/Hamadakram/Ratifier/blob/master/app/screencast.gif)
## Download
Grab via Gradle:
```
compile 'com.irozon.ratifier:ratifier:1.0.0'
```
Or Maven:
```
<dependency>
  <groupId>com.irozon.ratifier</groupId>
  <artifactId>ratifier</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
``` 
## How do i use Ratifier?
To use Ratifier as your form validator, use RatifierEditText and Ratifier will handle everything.
Example for Email:
```java
 <com.irozon.ratifier.RatifierEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="textEmailAddress"
        app:emptyMessage="Enter email"
        app:invalidMessage="Enter valid email"
        app:required="true" />
```
And to validate use
```java
Ratifier.Valid result = Ratifier.getValidity(this);
if (result.isValid()) { // Form is valid
    Toast.makeText(this, "Email is valid", Toast.LENGTH_SHORT).show();
} else { // Form is not valid
    Toast.makeText(this, ratify.getErrorMsg(), Toast.LENGTH_SHORT).show();
}
```
## Attributes
Following are the attributes used by **RatifierEditText** for validation

| Attribute | Descripion |
| ------ | ------ |
| required | If field is required for validation or not (true/false) |
| emptyMessage | Message for empty field |
| invalidMessage | Message for invalid field |
| inputType | **textEmailAddress**, **textPassword** for email and password validation. Ratifier will validate for match password also if two fields with inputType **textPassword** are provided. In case of invalidation, Invalid message must be provided |
| minCharacters | Minimum characters. Invalid message must be provided for this. |
| regex | For validation using regex like valid phone number, credit card, IP address etc. Invalid message must be provided for this. |

**Values can be set from activity by:**
```java
ratifierEditText.setEmptyMessage("Filed is empty");
ratifierEditText.setInvalidMessage("Password should be greater than 4 characters");
ratifierEditText.setRegex("/^(\+\d{1,3}[- ]?)?\d{10}$/"); // Regex for valid mobile number
ratifierEditText.setMinCharacters(4);
```
Remember to give regex from **Strings.xml**

## Ratifier Results
Ratifier validity result will give us:
```java
 Ratifier.Valid result = Ratifier.getValidity(this);
 boolean isValid = result.isValid(); // boolean - If the result is valid or not.
 String errorMessage = result.getErrorMsg(); // String - Error Message if result is not valid.
 RatifierEditText ratifierEditText = result.getField(); // Will return RatifierEditText which is not valid.
```
 - ***isValid()*** - boolean - If the result is valid or not.
 - ***getErrorMsg()*** - String - Error Message if result is not valid.
 - ***getField()*** - RatifierEditText - Will return **RatifierEditText** which is not valid


## Some Regex examples:

**Mobile Number**: /^(\+\d{1,3}[- ]?)?\d{10}$/

**Email Address**: /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/

**URL**: /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/

**IP Address**:/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/

etc

## Authors

* **Hammad Akram** - (https://github.com/hamadakram)
* **Shafqat Jamil** - (https://github.com/shafqatjamil)

## Licence
```
Copyright 2017 Irozon, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
