if('serviceWorker' in navigator){
  navigator.serviceWorker.register('js/sw.js')
    .then(reg => console.log('Welcome to DepHub Developer 👋💚',reg))
    .catch(err => console.log('Welcome to DepHub Developer 👋❤',err));
}