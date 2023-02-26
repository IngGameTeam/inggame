const mineflayer = require('mineflayer')


var createBot = function(name) {
    let bot = mineflayer.createBot({
        host: 'localhost',
        username: name,
        port: 25555,
    })
    bot.on('chat', (username, message) => {
      if (username === bot.username) return
      bot.chat(message)
    })
    bot.on('kicked', console.log)
    bot.on('error', console.log)
}


let amount = 300
for(let i = 0; i < amount; i++) {
    setTimeout(function() {
        createBot("test" + i);
    }, 50 * i);
}
