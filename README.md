# UPočasí

    předběžný přístup

- [UPočasí](#upoas)
  - [Popis projektu](#popis-projektu)
  - [Co to umí?](#co-to-um)
  - [Technologie, na kterých to běží](#technologie-na-kterch-to-b)
  - [Screenshoty](#screenshoty)

<span style="border:1px solid #59ff20">Odkaz na stránku https://krystofcejchan.cz/arduino_aiq_quality/beta/ </span>

## Popis projektu

Tato full-stacková open-source aplikace slouží k měření teploty, vlhkosti a kvalitě ovzduší pomocí Arduina a na něj
připojených senzorů a čipů. Data se měří každých 30 minut a ukládají se do MySQL databáze přes Spring Boot back-end.
Uživatel si může zobrazit nejnovější data, historii dat, žebříčky, grafy a i předpověď počasí přes webovou aplikaci
napsanou v HTML5, SCSS a Typescriptu za použití Angular frameworku.

## Co to umí?

- Zobrazování aktuálních i historických dat
- Zobrazení dat v grafu s časovou osou
- Zobrazování žebříčku dat (nejchladnější měření atd...)
- Zobrazování aktuální předpovědi počasí na 2 dny dopředu, včetně jednotlivých hodin
- Zasílání aktuální předpovědi na e-mail
- Nahlašování chybných měření uživateli
- Responzivita pro většinu obrazovek
- Dostupnost **API** pro vývojáře

## Technologie, na kterých to běží

- Na back-endu byl použit **framework Spring Boot** s programovacím jazykem **Java**
  - Back-end běží v cloudu díky **AWS Elastic Beanstalk**, který má doinstalované SSL certifikáty
- Vzhled a funkčnost webové stránky byl napsán v **HTML5**, **SCSS** a **TypeScriptu** za pomoci dodatečného **JavaScriptu** a
  to celé je zabaleno ve **frameworku Angular**
  - Celé je to hostované přes web-hosting
    - Web-hosting běží na serveru s upravenou konfigurací pro lepší zpracování požadavků pro Angular aplikaci
- Data a záznamy se ukládají do relačního databázového systému **MySQL**
  - Databázový systém je taky hostován na cloudu
- Data jsou měřena za pomocí čipů a senzorů připojených na **Arduino (NodeMCU)**
  - Kód běžící na Arduinu je napsán v **C++**

## Screenshoty

![image](https://user-images.githubusercontent.com/40124530/218798013-88c2d192-6a6d-4d48-9e74-e3bf1b6c4f8c.png)
