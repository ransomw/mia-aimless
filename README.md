# mia-aimless

[Motion Induced Aftereffect](doc/mather__mia_reloaded.pdf)
meanderings

![worf](doc/img/worf.gif)

### cli

##### js setup

js requires build

```
exp$ npm install && npm run build
```

##### REPL

`(go-devcards)` requires `node` available
in same shell as `lein repl` run, so

```
exp$ nvm use v8.7.0 && lein repl
```

from the REPL,

* `(go)` starts the application on port `10555`
* `(go-devcards)` starts the "visual REPL" on `3449`

### status

this repository currently consists of a barely initialized
experimental setup proof-of-concept for data collection
via websockets
