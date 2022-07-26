common commands:
menu enter <menu name>
menu exit
menu show-current

register menu:
user register --username <username> --nickname <nickname> --password <password>
user login --username <username> --password <password>

main menu:
user logout
create game
play game

profile menu:
profile change --nickname <nickname>
profile change --password --current <current password> --new <new password>

game menu:
select unit combat --x <x> --y <y>
select unit noncombat --x <x> --y <y>
select city --name <name>
select city --x <x> --y <y>
-----------------------------------------------------------
unit moveto --x <x> --y <y>
unit sleep
unit alert
unit fortify
unit heal
unit wake
unit found city --name <name>
unit garrison
unit cancel
unit setup
unit attack city --x <x> --y <y>
unit attack combat --x <x> --y <y>
unit attack noncombat --x <x> --y <y>
unit delete
unit build
unit remove <jungle/forests/marsh>
unit repair
unit pillage
-----------------------------------------------------------
info research
info units
info cities
info demographics
info notifications
info military
info economic
-----------------------------------------------------------
city screen
city output
city lock citizens
city remove citizens
city buy tile
city construction
city attack combat --x <x> --y <y>
city attack noncombat --x <x> --y <y>
-----------------------------------------------------------
cheat increase --amount <amount>
cheat teleport --x <x> --y <y>
cheat finish research
cheat reveal --x <x> --y <y>
-----------------------------------------------------------
map show position --x <x> --y <y>
map move <right/left/up/down> --c <c>
-----------------------------------------------------------
end turn
