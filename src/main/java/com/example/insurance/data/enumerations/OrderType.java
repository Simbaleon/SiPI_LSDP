package com.example.insurance.data.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {
	TEXTS("Тексты"),
	DESIGN("Дизайн"),
	PROGRAMMING("Программирование"),
	AUDIO("Аудио"),
	VIDEO("Видео"),
	MARKETING("Маркетинг"),
	GAMEDEV("Разработка игр"),
	ANIMATION("Анимация и флеш"),
	GRAPHIC3D("3Д Графика"),
	TRANSLATION("Переводы"),
	PHOTO("Фотография"),
	ENGINEERING("Инжиниринг"),
	ARCHITECTURE("Архитектура"),
	POLYGRAPHY("Полиграфия"),
	MANAGEMENT("Менеджмент"),
	MOBILEDEV("Мобильные приложения"),
	TELEGRAMBOT("Телеграм боты");

	private String uiValue;

}
