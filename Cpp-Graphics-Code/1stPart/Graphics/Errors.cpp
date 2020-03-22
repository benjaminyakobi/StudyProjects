#include "Errors.h"
#include <iostream>
#include <SDL/SDL.h>
#include <cstdlib>

//Helper function
void fatalError(std::string errorString) {
	std::cout << errorString << std::endl;
	std::cout << "Enter any key to quit...";
	int temp;
	std::cin >> temp;
	SDL_Quit(); //Quit SDL Systems
	exit(304);
}