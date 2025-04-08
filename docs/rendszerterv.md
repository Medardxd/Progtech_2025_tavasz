# Rendszerterv
## 1. A rendszer célja
Az alkalmazás célja egy változatos és szórakoztató szerencsejáték szimuláció biztosítása, amelyben a felhasználók különböző játékokat próbálhatnak ki. A fő cél az, hogy az alkalmazás vonzó alternatívát nyújtson a szerencsejáték függőséggel küzdők számára, segítve őket abban, hogy inkább biztonságos környezetben játszanak. A rendszer céljai közé tartozik továbbá a játékosok nyereség/veszteség arányának nyomon követése, az elemzések és statisztikák biztosítása a játékokról, valamint a felhasználói élmény folyamatos javítása és a felhasználói visszajelzések gyűjtése a fejlesztési folyamat során.
## 2. Projektterv

### 2.1 Projektszerepkörök, felelőségek:
  * Üzleti szereplő:
	  -   Megrendelő:
		  -  Troll Ede
     
### 2.2 Projektmunkások és felelőségek:
   * Frontend és backend:
     - Bodnár Máté
     - Herbák Marcell
   * Tesztelés:
     - Bodnár Máté
     - Herbák Marcell
     
### 2.3 Ütemterv:

|Funkció                  | Feladat                                | Prioritás | Becslés (nap) | Aktuális becslés (nap) | Eltelt idő (nap) | Becsült idő (nap) |
|-------------------------|----------------------------------------|-----------|---------------|------------------------|------------------|---------------------|
|Rendszerterv             |Megírás                                 |         1 |             1 |                      1 |                1 |                   1 |
|Program                  |Prototípus elkészítése                  |         2 |             3 |                      3 |                2 |                   1 |
|Program                  |Tesztelés                               |         3 |             1 |                      1 |                1 |                   1 |

### 2.4 Mérföldkövek:
   *   05.10. Projekt elkezdése
   *   05.11. Alap prototípus elkészítése
   *   05.12. Végleges prototípus elkészítése
   *   05.12. Tesztelés
   *   05.13. Bemutatás és átadás

## 3. Üzleti folyamatok modellje

### 3.1 Üzleti szereplők
Az alkalmazás regisztráció vagy bejelentkezés után válik elérhetővé, bárki tud regiszrálni. Minden felhasználó ugyanolyan jogkörrel rendelkezik.

### 3.2 Üzleti folyamatok
Az alkalmazás indulását követően a felhasználónak be kell jelentkeznie a funkciók eléréséhez.
- Általános folyamatok:
     - Regisztrálni az oldalra a megfelelő adatok magadásával.
     - Bejelentkezni az oldalra a regisztráció során megadott megfelelő adatokkal.
     - Bármikor kilépni az alkalmazásból.
     - Játék kiválasztása.
- Pénzfeldobó játék folyamatok:
	- Tét megtétele.
	- Tipp módosítása.
	- Legutóbbi dobások megtekintése.
	- Tipp elfogadása és részvétel a "sorsolásban".
- Kockadobó játék folyamatok:
  - Tét megtétele.
  - Páros/Páratlan számok tippelése.
  - Kisebb, egyenlő mint 3 vagy nagyobb mint 3 számok tippelése.
  - Pontos szám tippelése.
  - Tipp elfogadása és részvétel a "sorsolásban".

## 4. Követelmények

### Funkcionális követelmények

| ID | Megnevezés               | Leírás                                                                                                                                                                                   |
|----|--------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| K1 | Bejelentkezési ablak     | A felhasználónak egyes funkciók elérése előtt azonosítania kell magát, mielőtt elérhetné azokat.                                                                                         |
| K2 | Regisztráció             | A felhasználó itt tudja regisztrálni magát.                                                                                                                                              |
| K3 | Játékmód választás       | A fehasználó kiválaszthatja, mely játékmódot szeretné játszani.                                                                                                                          |
| K4 | Pénzfeldobó játék        | A pénzfeldobó játékban a felhasználó megteheti tétjét, hogy a szimulált pénzérme a feje vagy írása mutat felfelé, és az alapján kapja vissza megtett tétjét, amelyet tud módosítani is.  |
| K5 | Kockadobó játék          | A kockadobó játékban a felhasználó megteheti tétjét, hogy a szimulált dobókocka különböző tulajdonságaira, és az alapján kapja vissza megtett tétjét, amelyet tud módosítani is.         |
 
### Nemfunkcionális követelmények

| ID | Megnevezés                             | Leírás                                                                                                              |
|----|----------------------------------------|---------------------------------------------------------------------------------------------------------------------|
| K6 | Átlátható, könnyen kezelhető felület   | A felületek könnyen használhatóak, átláthatóak legyenek, intuitívak, illetve ne legyenek zsúfoltak.                 |
| K7 | Tervezési minták használata            | Az alkalmazás forráskódja tartalmazzon legalább 2 tervezési mintát. Mi esetünkben ez a Stratégia és a Command lesz. |

### Támogatott eszközök

 * Bármely Java alkalmazás futtatásra képes eszköz. Például otthoni számítógép, laptop, tablet. Preferáltabb Windows alapú rendszer.

## 5. Funkcionális terv

### 5.1 Rendszerszereplők
 - Felhasználó
   - Tétet tehet meg, és játszhat a játékmódokkal
 - "Gép"
   - Sorsolja az eredményeket

### 5.2 Menühierarchiák
- Főoldal (Bejelentkezés és Regisztráció)
- Pénzfeldobó játék
- Dobókocka játék

## 6. Fizikai környezet

### Vásárolt szoftverkomponensek, valamint esetleges külső rendszerek
- A rendszer fejlesztéséhez és működéséhez nem szükségesek kereskedelmi szoftverkomponensek.

### Hardver topológia
A rendszer működtetéséhez olyan számítógép szükséges, amely rendelkezik legalább az alábbiakkal:
- Windows 10 vagy Windows 11 operációs rendszer
- Legalább 4 GB RAM, ajánlott 8 GB
- Legalább 1 GHz-es processzor, ajánlott 2+ magos CPU
- Helyi hálózati vagy internetkapcsolat, ha a MySQL adatbázis távoli gépen fut

### Fizikai alrendszerek
Kliens gépek:
- Felhasználói oldalon Windows 10/11 operációs rendszerrel rendelkező számítógépek, amelyeken a WPF alkalmazás fut. A kliens közvetlenül kapcsolódik a szerver gépen elérhető MySQL adatbázishoz.

Szerver (Host) gép:
- A MySQL adatbázist kiszolgáló gép. Ez lehet egy különálló gép a hálózaton, vagy akár egy kliensgépen lokálisan futtatott adatbázis is. A szerver biztosítja az adatok központi tárolását és elérhetőségét.

### Fejlesztő eszközök
- Visual Studio 2022 Community Edition – a WPF alkalmazás fejlesztésére
- MySQL Workbench – az adatbázis tervezésére és kezelésére
- MySQL Server – az adatbázis kiszolgálásához
- MySQL Connector/NET – a WPF alkalmazás adatbáziskapcsolatához
- Git + GitHub – verziókezeléshez

## 8. Architekturális terv

### Webszerver
- A rendszer nem igényel külön webszervert, mivel egy WPF alapú asztali alkalmazásról van szó.

### Adatbázis rendszer
- MySQL alapú adatbázis rendszer, amelyhez az alkalmazás a MySQL Connector for .NET segítségével kapcsolódik.

### A program elérése, kezelése
- A WPF-alapú asztali alkalmazás közvetlenül kapcsolódik a MySQL-adatbázishoz.

- A fejlesztéshez Visual Studio és .NET 6 vagy újabb szükséges.

- Az adatbázis eléréshez szükséges MySQL Connector és a megfelelő connection string konfigurálása.


## 9. Adatbázis terv

![dbterv](https://github.com/herbakmarcell/gambasim_progtech/assets/128597041/139356a1-df93-4e2a-9172-66dbd2bd8f31)

## 10. Implementációs terv
A projekt két fő részre oszlik: a frontendre és a backendre.
Frontend: A WPF technológiát használja, XAML alapú nézetekkel és MVVM architektúrával.
Backend: A .NET keretrendszer segítségével készült adatkezelő réteg valósítja meg az adatbázis-műveleteket, beleértve az autók, bérlések, felhasználók kezelését.
A forráskódban egységesen az angol nyelvet használjuk az osztály-, változó- és metódusnevekben.
Az alkalmazás Visual Studio-ban fejleszthető és építhető.

## 11. Tesztterv
A rendszer funkcionalitásának ellenőrzése unit tesztek és manuális felhasználói tesztek segítségével történik.
Minden funkció külön tesztesetként valósul meg, lehetőség szerint xUnit/NUnit tesztkörnyezetben.

### Tesztesetek

#### Tesztelés módja: Unit Teszt

 | Teszteset      | Elvárt eredmény                                                                                            | 
 |----------------|------------------------------------------------------------------------------------------------------------| 
 | Regisztráció   | A felhasználó megadott adatokkal sikeresen regisztrál a rendszerbe.                                        |
 | Bejelentkezés  | A felhasználó helyes adatokkal be tud lépni.                                                               |
 | Autó foglalása  | Ha az autó szabad és a felhasználó jogosult, a foglalás létrejön.                                         |
 | Foglalás törlése | A felhasználó törölni tudja a saját aktív foglalását.                                                    |
 | Autó hozzáadása | Új autó hozzáadása a rendszerhez                                                                          |

## 12. Telepítési terv

**Fizikai telepítési terv**:


**Szoftver telepítési terv**:


## 13. Karbantartási terv
