# Project Name: News Portal


### Project Description
A Java/Spark application with a rest REST API for querying and retrieving scoped news and information. News, articles and posts are available to all users without navigating into any department, and others that are housed or classified within departments.



### Setup Instructions

* To run the application, first install the java development kit from `https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html`
* Install gradle.
* Next clone the project using `$ git clone https://github.com/deepeters/douglas-fir` to your preferred folder.
* In the command prompt, navigate to cloned folder and use the following command to run the app `$ gradle run`
* Open the browser and enter the address `localhost:4567`

* * Install PostgreSQL. for help installing ==> https://www.youtube.com/watch?v=-LwI4HMR_Eg
  * Open up your terminal and type the following commands(only works if postgreSQL is installed)
  
 ## DATABASE NAME 
 `\c newsportal;`
 `\c newspotal_test;`


## End points
| URL                                            | HTTP Verb   |                                 DESCRIPTION|
|--                                              |  ---        |                                   ---      |
|/users                                          |get          |     Get staff                              |
|/departments                                    |get          |     Get departments                        |
|/news/general                                   |get          |      get all news                          |
|/user/:id/departments                           |get          |      get users in a department             |
|/departments/:id/user                           |get          |     get depertments users                  |
|/depertment/:id                                 |get          |     get depertment using id                |
|/users/:id                                      | get         |     get users using id                     |
|/users/new                                      |post         |     add a new user                         |
|/departments/new                                |post         |     add a new department                   |
|/news/new/general                               |post         |     add news                               |
|/news/new/department                            |post         |    add news in department                  |
|/department/:id                                 |post         |    add department and give it an id        |
|/users/:id                                      |post         |    add user and give it an id              |

# Contact Information:
### Author: [DIANA DIAN KERUBO](https://github.com/diana3664)

         Email: dianamus2017@gmail.com

### Technology Used
1. Java
2. HTML
3. CSS

### Frameworks Used
1. Gradle
2. Spark
3. Maven
4. Junit
5. Handlebars

### Libraries Used
1. Bootstrap
2. Material Design Bootstrap.

### LICENSE: [MIT LICENSE](https://raw.githubusercontent.com/diana3664/news-portal/master/LICENSE)