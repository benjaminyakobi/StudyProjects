#include "MainGame.h"
#include <iostream>
#include <string>

//Helper function
void fatalError(std::string errorString) {
	std::cout << errorString << std::endl;
	std::cout << "Enter any key to quit...";
	int temp;
	std::cin >> temp;
	SDL_Quit(); //Quit the game
}

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
	SDL_Init(SDL_INIT_EVERYTHING); //inside brackets: flag what to init
	this->_window = SDL_CreateWindow("Game Engine", SDL_WINDOWPOS_CENTERED, 
		SDL_WINDOWPOS_CENTERED, this->_screenWidth, this->_screenHeight, 
		SDL_WINDOW_OPENGL); //return a pointer to a window

	if (this->_window == nullptr) {
		fatalError("SDL Window could not be created.");
	}

	SDL_GLContext glContext = SDL_GL_CreateContext(this->_window);
	if (glContext == nullptr) {
		fatalError("SDL_GL context could not be created.");
	}

	GLenum error = glewInit(); //Initialize glew and setup extensions. returns GLenum number
	if (error != GLEW_OK) {
		fatalError("Could not initialize glew.");
	}

	SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1); //tells sdl that we want double buffer and 1 we're drawing to
	
	glClearColor(0.0f, 0.0f, 1.0f, 1.0f); //set background color
}

void MainGame::gameLoop()
{
	while (this->_gameState != GameState::EXIT) {
		processInput();
		drawGame();
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

void MainGame::drawGame()
{
	glClearDepth(1.0);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //Clears the screen, "|" bitwise OR operator
	
	glEnableClientState(GL_COLOR_ARRAY);
	glBegin(GL_TRIANGLES);
	glColor3f(1.0f, 0.0f, 0.0f);
	glVertex2f(0, 0);
	glVertex2f(0, 500);
	glVertex2f(500, 500);

	glEnd();

	SDL_GL_SwapWindow(this->_window);
}
