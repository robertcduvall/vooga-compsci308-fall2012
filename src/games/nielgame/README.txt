README
Niel Lebeck

I wrote two games instead of one for this assignment. This is because I wanted to demonstrate two
aspects of our overall design: 1) the ability to quickly create games using only the level editor,
and 2) the ability to customize the game's functionality using LevelPlugins and Conditions.

1)
MarioStyleGame demonstrates the first point. The level was created entirely in the level editor,
and absolutely no code was written. Its creation in the level editor took a matter of minutes.

2)
ReverseGravityGame demonstrates the second point. It uses a new custom LevelPlugin (GravityPlugin)
to apply gravity upwards instead of downwards, and it uses a new custom Condition
(PlayerInZoneCondition) to have the player win by passing through a specific region of the level--
in this case, the door at the top of the screen. Note that the GravityPlugin and the new Condition
each take all the information they need in their constructors, and they are added to the Level
using the standard addPlugin() and addCondition() methods in the Level's API. As a result, these
two new classes demonstrate the flexibility of the LevelPlugin and Condition hierarchies.

These new plugins are designed to be general. However, because I created these custom plugins just
when I was making this game, they are not yet supported in the level editor. As a result, I had to
add the plugins using code in the ReverseGravityGame launcher class. The fact that this "factory
code" is hardcoded does not diminish the extensible nature of the plugins themselves--once the
plugins are added to the level, whether through a factory or explicitly, the level can deal with
the plugins in an extensible, general manner and use their behaviors to provide new functionality.
