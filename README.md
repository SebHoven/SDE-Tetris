# SDE-Tetris
Java console tetris game - gemaakt door Sidney Felius en Seb van den Hoven

## Samenwerking
We hebben ervoor gekozen om van elk type design pattern er allebij één van te implementeren. 
Omdat de school meerdere dagen gesloten was en veel van de lessen online gehouden werden hebben we veel vanuit thuis moeten doen.

## Design Patterns

### Creational Patterns

#### Factory
Voor het maken van de individuele tetris blokken heb ik gebruik gemaakt van een factory, dit is zodat ik niet elke keer voor elk blok een heel stuk code dupliceer. Daarnaast is het ook handig als je het met nieuwe blokken uit wil breiden(wat niet zo snel het geval zal zijn bij tetris in dit geval), dit komt omdat het maken van de blokken wordt gesplits met echt het gebruiken van de blokken.

#### Singleton
