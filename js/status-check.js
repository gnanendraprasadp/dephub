getDependency();

async function getDependency() {
  await fetch("https://gnanendraprasadp.github.io/dephub/json/dependency.json")
    .then((response) => response.json())
    .then((json) => {
      for (i = 0; i < json.length; i++) {
        getStatusCode(json[i].github_link);
      }
    });
}

async function getStatusCode(link) {
  const response = await fetch(link);
  if (response.status === 200) {
    console.log(
      "\u001b[1;32m [SUCCESS] " + link + " has response code " + response.status
    );
  } else {
    console.error(
      "\u001b[1;31m [FAILURE] " + link + " has response code " + response.status
    );
  }
}
