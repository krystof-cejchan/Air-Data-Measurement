# UPočasí
![workflow passing?](https://github.com/krystof-cejchan/Air-Data-Measurement/actions/workflows/master_airq-app.yml/badge.svg)

## Obsah
- [UPočasí](#upočasí)
  * [Obsah](#obsah)
  * [Popis projektu](#popis-projektu)
  * [Co to umí?](#co-to-umí)
  * [Technologie, na kterých to běží](#technologie-na-kterých-to-běží)
  * [Screenshoty](#screenshoty)
    + [Úvodní stránka](#úvodní-stránka)
    + [Nejnovější data](#nejnovější-data)
    + [Historie](#historie)
    + [Grafy](#grafy)
    + [Žebříčky](#žebříčky)
    + [Předpověď počasí](#předpověď-počasí)
    + [Detaily dat](#detaily-dat)

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
- Dostupnost **API** pro vývojáře(may be coming soon)

## Technologie, na kterých to běží

- Na back-endu byl použit **framework Spring Boot** s programovacím jazykem **Java 17**
  - Back-end běží v cloudu díky **Microsoft Azure Web App** s doinstalovanými SSL certifikáty
- Vzhled a funkčnost webové stránky byl napsán v **HTML5**, **SCSS** a **TypeScriptu** za pomoci dodatečného **JavaScriptu** a
  to celé je zabaleno ve **frameworku Angular**
  - Celé je to hostované přes web-hosting
    - Web-hosting běží na serveru s upravenou konfigurací pro lepší zpracování požadavků pro Angular aplikaci
- Data a záznamy se ukládají do relačního databázového systému **MySQL**
  - Databázový systém je taky hostován na cloudu
- Data jsou měřena za pomocí čipů a senzorů připojených na **Arduino (NodeMCU)**
  - Kód běžící na Arduinu je napsán v **C++**

## Screenshoty
### Úvodní stránka
![main_page_img](https://user-images.githubusercontent.com/40124530/222465453-caf85467-4b7b-4c4e-a6b5-b8977748a1fa.png)

### Nejnovější data
![image](https://user-images.githubusercontent.com/40124530/222467797-1f0b8ecf-cf9f-43aa-a2f6-53b0f16a83e9.png)

### Historie
![history_page_img](https://user-images.githubusercontent.com/40124530/222465847-4225f37b-1a23-41b6-ad46-a0d0d4fffa19.png)

### Grafy
![image](https://user-images.githubusercontent.com/40124530/222468061-aaefe3ab-0005-40d1-bbf3-6e52f0625557.png)

### Žebříčky
![image](https://user-images.githubusercontent.com/40124530/222466122-d938f806-97a1-45be-adcc-1944b94659df.png)

### Předpověď počasí
![image](https://user-images.githubusercontent.com/40124530/222466376-4147e19e-fac6-497b-8e25-54a42a75b885.png)

### Detaily dat
![image](https://user-images.githubusercontent.com/40124530/222467129-81644761-649c-46cb-a2d8-58a795702545.png)