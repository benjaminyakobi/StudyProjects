#include "MainGame.h"
#include <iostream>


MainGame::MainGame()
{
	this->_window = nullptr;
	this->_screenWidth = 1024;
	this->_screenHeight = 768;
	this->_gameState = GameState::PLAY;
}


MainGame::~MainGame()
{
}

void MainGame::run()
{
	initSystems(); //first initialize the system
	gameLoop(); //and after run game loop
}

void MainGame::initSystems()
{
	SDL_Init(SDL_INIT_EVERYTHING); //inside brackets: flag how to init
	this->_window = SDL_CreateWindow("Game Engine", SDL_WINDOWPOS_CENTERED, 
		SDL_WINDOWPOS_CENTERED, this->_screenWidth, this->_screenHeight, 
		SDL_WINDOW_OPENGL); //return a pointer to a window
}

void MainGame::gameLoop()
{
	while (this->_gameState != GameState::EXIT) {
		processInput();
	}
}

void MainGame::processInput()
{
	SDL_Event evnt; //"event" is a reserved word
	/*SDL_PollEvent Returns 1 if there is a pending event or 0 if there are none available: https://wiki.libsdl.org/SDL_PollEvent
	we must send evnt by reference*/
	while (SDL_PollEvent(&evnt)) {
		switch (evnt.type) { //types of events: https://wiki.libsdl.org/SDL_Event
			case SDL_QUIT:
				this->_gameState = GameState::EXIT;
				break;

			case SDL_MOUSEMOTION:
				std::cout << evnt.motion.x << " " << evnt.motion.y << std::endl;
				break;
		}
	}
}
