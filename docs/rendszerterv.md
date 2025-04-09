# Rendszerterv
## 1. A rendszer célja
Az alkalmazás célja egy egyszerűen kezelhető, grafikus felülettel rendelkező kocsi kereskedés szimulálása, amely lehetővé teszi a felhasználók számára, hogy megtekinthessék a kereskedésben elérhető autókat, új járműveket adhassanak hozzá, meglévő járműveket szerkeszthessenek vagy törölhessenek, valamint vásárlásokat szimulálhassanak.
A rendszer egy modern, digitális járműkereskedői megoldást kínál, amely segíti a kisebb autókereskedéseket abban, hogy átláthatóan és hatékonyan kezelhessék kínálatukat.
## 2. Projektterv

### 2.1 Projektszerepkörök, felelőségek:
  * Üzleti szereplő:
	  -   Megrendelő:
		  -  Herbák Marcell
     
### 2.2 Projektmunkások és felelőségek:
   * Frontend és backend:
     - Bobák Martin
     - Horváth Medárd
   * Tesztelés:
     - Bobák Martin
     - Horváth Medárd
     
### 2.3 Ütemterv:

|Funkció                  | Feladat                                | Prioritás | Becslés (nap) | Aktuális becslés (nap) | Eltelt idő (nap) | Becsült idő (nap) |
|-------------------------|----------------------------------------|-----------|---------------|------------------------|------------------|---------------------|
|Rendszerterv             |Megírás                                 |         1 |             1 |                      1 |                1 |                   1 |
|Program                  |Prototípus elkészítése                  |         2 |             5 |                      5 |                3 |                   3 |
|Program                  |Tesztelés                               |         3 |             2 |                      2 |                1 |                   1 |

### 2.4 Mérföldkövek:
   *   04.05. Projekt elkezdése
   *   04.09. Dokumentáció elkészítése
   *   04.11. Béta verzió elkészítése
   *   04.12. Végleges prototípus elkészítése
   *   04.12. Tesztelés
   *   04.13. Bemutatás és átadás

## 3. Üzleti folyamatok modellje

### 3.1 Üzleti szereplők
Az alkalmazás használata regisztrációhoz kötött. A felhasználók kétféle jogosultsággal rendelkezhetnek:

* Adminisztrátor: teljes hozzáféréssel rendelkezik az autók adatainak kezeléséhez (hozzáadás, szerkesztés, törlés), valamint vásárlás szimulálásához.

* Felhasználó: megtekintheti az autók listáját és szimulált vásárlásokat hajthat végre, de nem módosíthatja a járművek adatait.

### 3.2 Üzleti folyamatok
Az alkalmazás indulását követően a felhasználó bejelentkezik a megfelelő hitelesítő adatokkal. A bejelentkezés alapján eltérő funkciók válnak elérhetővé:
- Adminisztrátorok által elérhető funkciók:
     - Új autó hozzáadása (márka, modell, évjárat, ár megadásával).
     - Meglévő autó adatainak módosítása (például ár vagy évjárat szerkesztése).
     - Autó törlése a kínálatból.
     - Vásárlási folyamat szimulálása.
     - Eladott autók listájának megtekintése.
- Felhasználók által elérhető funkciók:
	- Autók listájának megtekintése (márka, modell, évjárat, ár adatokkal).
	- Vásárlás szimulációja: autó kiválasztása és megvásárlása (az autó eltávolítása a kínálatból vagy áthelyezése eladott listára).
- Általános folyamatok:
  - Bejelentkezés a rendszerbe a felhasználónév és jelszó megadásával.
  - Kilépés az alkalmazásból.

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
![alt text](image.png)

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
- A felhasználónak szüksége van egy Windows 10 vagy újabb verziójú operációs rendszerrel rendelkező számítógépre.
- A MySQL adatbázisszerver lehet helyi vagy távoli.

**Szoftver telepítési terv**:
A felhasználónak telepítenie kell a következőket:
- .NET Desktop Runtime (6.0 vagy újabb)
- A WPF-alkalmazás futtatható állománya
Fejlesztőknek:
- Visual Studio 2022+
- MySQL for Visual Studio
- MySQL Connector/NET
Az adatbázist előre létrehozott SQL szkriptek segítségével lehet inicializálni.

## 13. Karbantartási terv
A felhasználók adatainak védelmét és jogosultságait biztonságos bejelentkezési mechanizmus biztosítja.
Rendszeres adatbázis mentések javasoltak.
A hibákat és visszajelzéseket a fejlesztői csapat dokumentálja, és azok alapján frissítéseket biztosít.
A karbantartás során figyelembe vesszük:
- Funkcionális hibák
- Felhasználói élményt érintő hibák
- Teljesítményproblémák