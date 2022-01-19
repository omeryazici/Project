İki dizinde birer proje bulunuyor. Bir tanesi hayali bir alışveriş sitesinin API'sini simüle ediyor. Bir tanesi de çok basitçe bir ödeme sistemini simüle ediyor. Bazı servisler ve fonksiyonlar tamamlanmış durumda, bazılarınınsa tamamlanması gerekiyor. Shop projesinde Sepet Oluştur, Sepete Ekle ve Sepetten Çıkar servisleri çalışır vaziyette. Wallet projesinde ise kişiye ait cüzdanların bilgisinin dönüldüğü servis çalışır vaziyette. Uygulamada in-memory H2 database kullanılıyor. Ekstra bir kurulum yapmadan direkt çalıştırılabilir. In-memory olduğundan uygulama her yeniden başlatıldığında tablolar yeniden oluşturulup, data.sql dosyaları içerisindeki veriler initial value olarak insert ediliyor. Bu kapsamda userId gibi bilgiler oradan bulunabilir.

Yapılmasını beklediğimiz şey Shop projesindeki eksik olan PAY servisinin yazılması ve bu ihtiyaca göre Wallet servisindeki eksikliklerin giderilmesi. 
Bu servisten beklentilerimiz, 

- Wallet servisinden kullanıcıya ait cüzdanların bilgisinin alınması
- En uygun cüzdandan paranın kesilmesi, bir cüzdan yeterli değilse parçalı ödeme yapılması.
- Hata kontrollerinin yapılması