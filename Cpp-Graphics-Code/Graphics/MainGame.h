#pragma once //protects from circular includes
#include <SDL/SDL.h>
#include <GL/glew.h>

enum class GameState {PLAY, EXIT};

class MainGame
{
public:
	MainGame();
	~MainGame();
	void run();

private:
	void initSystems();
	void gameLoop();
	void processInput();
	void drawGame();
	SDL_Window* _window; //resize & controlling the window
	int _screenWidth;
	int _screenHeight;
	GameState _gameState;
};

