TODO:
* Add month + expected delivery date to emails
* Secure API Gateway
* Make sure I get notified if anything breaks

Setup:

script/ contains some rough scripts to create the lambda function.

You'll also need to create a API gateway to receive the webhook from lob. resources/ contains some mappings + schemas for that

You'll also need to create lob data: 2 addresses, 1 bank account and get their tokens. Bank accounts need to be validated.
