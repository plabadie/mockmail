## What is mockmail

* Mockmail is a developer tool. 
* Mockmail is a fake smtp server.
* When mockmail recieves a mail to relay, it simply saves it to individual files on disk.
* By design, Mockmail does not and can not send emails.


## Why would you want to use this tool.

* You are developing an application that sends emails and you need a smtp to send them to.
* Your application possibly sends email to many different recipients.  It can be a complex and time consuming to access many email accounts for the purpose of testing.  With mockmail, just open the saved email files and you are done.
* You might have emails of real users in your test data and you do not want them to receive test emails.

## Installation

Download the application [here](/sdfgsdfgs.zip)

Unzip the file in a folder of yout choosing.

To start mockmail with the default settings, simply invoke the startup script from the command line in the mockmail folder.


```
$ java -jar mockmail.jar
```

To stop mockmail, use ctrl+z

For possible command line options use the -help argument

```
$ java -jar mockmail.jar -help

usage: java -jar MockMail.jar
 -help                print this message
 -outdir <path>       Folder where the email files will be stored. Can be
                      relative to the working dir. Default : 'mail'
 -port <portnumber>   Port number where MockMail serves smtp requests.
                      Default : 2525.
```

