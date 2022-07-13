import smtplib
import os
addr  = os.path.abspath(os.getcwd())+"/ma.txt"
print(addr)
f = open(addr, "r")
sendTo = f.readline()[0:-1]
status = f.readline()[0:-1]
i = f.readline()[0:-1]
o = f.readline()[0:-1]
f.close()
print(sendTo)
# creates SMTP session
s = smtplib.SMTP('smtp.gmail.com', 587)

# start TLS for security
s.starttls()

# Authentication
s.login("employeemanagementnoreply@gmail.com", "ugxkwektfgtwpjiz")

SUBJECT = "Your attendance from "+i+" to "+o
TEXT = "Status : "+status
# message to be sent
message = 'Subject: {}\n\n{}'.format(SUBJECT, TEXT)

# sending the mail
s.sendmail("employeemanagementnoreply@gmail.com", sendTo, message)

# terminating the session
s.quit()
