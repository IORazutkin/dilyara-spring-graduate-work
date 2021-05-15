# Генерация сертификата для HTTPS
`"C:\Program Files\Java\jdk1.8.0_241\bin\keytool.exe" -genkeypair -alias baeldung -keyalg RSA -keysize 2048 -
storetype PKCS12 -keystore keystore.p12 -validity 3650`

###### _Пароль: 12345678_