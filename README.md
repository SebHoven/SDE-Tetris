# SDE-Tetris
Java console tetris game - gemaakt door Sidney Felius en Seb van den Hoven

## Samenwerking
We hebben ervoor gekozen om van elk type design pattern er allebij één van te implementeren. 
Omdat de school meerdere dagen gesloten was en veel van de lessen online gehouden werden hebben we veel vanuit thuis moeten doen.

## Design Patterns

### Creational Patterns

#### Factory
Ik heb de Factory pattern gebruikt om de verschillende tetris blokken aan te maken zonder dat er al te veel code duplicatie voor komt. Ik heb hiervoor de TetrisPiece interface aangemaakt waarin alle functies worden aangemaakt. Deze functies worden daarna verder uitgewerkt in de AbstractTetrisPiece functie met nog wat extra code om de rotate functie te laten werken. De concrete tetris blokken extenden dan de AbstractTetrisPiece en in de constructor wordt de vorm van het tetris blok opgeslagen in de shape variabele. Dan de factory interface heet PieceFactory die de createTetrisPiece functie aanmaakt. In AbstractPieceFactory wordt deze functie beschreven en wordt er een willekeurige keuze gemaakt uit de 7 tetris blokken.
Facories zijn ook handig als je het met nieuwe blokken uit wil breiden(wat niet zo snel het geval zal zijn bij tetris in dit geval), dit komt omdat het aanmaken van de verschillende tetris blokken wordt gescheiden van het echte gebruik van de tetris blokken in de code.

#### Singleton

### Structural Patterns 

#### Façade

Ik heb de façade pattern gebruikt om de GameFacade klasse aan te maken. In deze klasse staan versimpelde versies van de functies die ik in GameController heb geschreven. Een façade zorgt voor een versimpelde interface tot een complexere groep van functies/klassen. Dit zorgt er ook voor dat de code makkelijker is om bij te houden en makkelijker voor andere mensen om er aan te werken.

### Behavioral Patterns

#### Command

Ik heb de Command pattern gebruikt om meerdere acties aan te maken. Hiervoor heb ik de interface Command aangemaakt die een methode execute() aanmaakt. Deze methode wordt in de concrete commands, zoals MoveRightCommand overschreven door er de functie van de beweging aan te roepen. Deze Klassen worden daarna in de InputHandler gebruikt zodat de beweging gekoppeld wordt aan een bepaalde string(+ enter), als je het vergelijkt met het voorbeeld op refactoring guru is de InputHandler de invoker. De Receiver is in dit geval de GameController, hierin staat wat de tetris blokken precies moeten kunnen(beweging, rotatie etc.)
Commands zijn dus een handige manier om ervoor te zorgen dat de InputHandler los van de functionaliteit van de beweging staat en het zorgt ervoor dat het makkelijker is om je programma uit te breiden met andere commands door een nieuwe klasse aan te maken, deze te omschrijven in de Gamecontroller en hem aan te roepen in de InputHandler.
