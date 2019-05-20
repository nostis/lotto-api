# lotto-api
REST api which helps in gathering informations about number lotteries (lotto in Poland)

Data used from: http://www.mbnet.com.pl/wyniki.htm

1. You have to have installed MySQL server.
2. In app properties (resources/application.properties file) you have to change url, username and password to these what you have.

Available queries; <br>
* /lotto
* /mini
* /multi

and inside them;
* ?page=**page**&size=**size**&sort=**sort**
* /search
  * /findByDrawNumber?drawNumber=**drawNumber**
