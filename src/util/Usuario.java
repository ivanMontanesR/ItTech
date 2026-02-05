package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Usuario {

	private static Scanner sc = new Scanner(System.in);

	public static int leerEntero() {
		int numero = 0;
		boolean valido = false;

		while (!valido) {
			try {
				numero = sc.nextInt();
				valido = true;
			} catch (InputMismatchException e) {
				System.out.println("Error: Debes introducir un número entero válido.");
				sc.nextLine();
			}
		}
		sc.nextLine();
		return numero;
	}

	public static int leerEnteroPositivo() {
		int numero = -1;
		boolean valido = false;

		while (!valido) {
			try {
				numero = sc.nextInt();
				if (numero >= 0) {
					valido = true;
				} else {
					System.out.println("Error: El numero debe ser mayor o igual a 0");
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: Debes introducir un número entero válido.");
				sc.nextLine();
			}
		}
		sc.nextLine();
		return numero;
	}

	public static int leerEnteroRango(int min, int max) {
		int numero = 0;
		boolean valido = false;

		while (!valido) {
			try {
				numero = sc.nextInt();

				if (numero >= min && numero <= max) {
					valido = true;
				} else {
					System.out.println("Error: El número debe estar entre " + min + " y " + max);
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: Debes introducir un número entero válido.");
				sc.nextLine();
			}
		}
		sc.nextLine();
		return numero;
	}

	public static double leerDouble() {
		double numero = 0.0;
		boolean valido = false;

		while (!valido) {
			try {
				numero = sc.nextDouble();
				valido = true;
			} catch (InputMismatchException e) {
				System.out.println("Error: Debes introducir un número decimal válido.");
				sc.nextLine();
			}
		}
		sc.nextLine();
		return numero;
	}

	public static float leerFloat() {
		float numero = 0.0f;
		boolean valido = false;

		while (!valido) {
			try {
				numero = sc.nextFloat();
				valido = true;
			} catch (InputMismatchException e) {
				System.out.println("Error: Debes introducir un número decimal válido.");
				sc.nextLine();
			}
		}
		sc.nextLine();
		return numero;
	}

	public static long leerLong() {
		long numero = 0L;
		boolean valido = false;

		while (!valido) {
			try {
				numero = sc.nextLong();
				valido = true;
			} catch (InputMismatchException e) {
				System.out.println("Error: Debes introducir un número entero largo válido.");
				sc.nextLine();
			}
		}
		sc.nextLine();
		return numero;
	}

	public static short leerShort() {
		short numero = 0;
		boolean valido = false;

		while (!valido) {
			try {
				numero = sc.nextShort();
				valido = true;
			} catch (InputMismatchException e) {
				System.out.println("Error: Debes introducir un número short válido (-32768 a 32767).");
				sc.nextLine();
			}
		}
		sc.nextLine();
		return numero;
	}

	public static byte leerByte() {
		byte numero = 0;
		boolean valido = false;

		while (!valido) {
			try {
				numero = sc.nextByte();
				valido = true;
			} catch (InputMismatchException e) {
				System.out.println("Error: Debes introducir un número byte válido (-128 a 127).");
				sc.nextLine();
			}
		}
		sc.nextLine();
		return numero;
	}

	public static boolean leerBoolean() {
		boolean valor = false;
		boolean valido = false;

		while (!valido) {
			try {
				valor = sc.nextBoolean();
				valido = true;
			} catch (InputMismatchException e) {
				System.out.println("Error: Debes introducir 'true' o 'false'.");
				sc.nextLine();
			}
		}
		sc.nextLine();
		return valor;
	}

	public static char leerChar() {
		char caracter = ' ';
		boolean valido = false;

		while (!valido) {
			try {
				String input = sc.nextLine();

				if (input.length() > 0) {
					caracter = input.charAt(0);
					valido = true;
				} else {
					System.out.println("Error: Debes introducir al menos un carácter.");
				}
			} catch (Exception e) {
				System.out.println("Error: No se pudo leer el carácter.");
			}
		}
		return caracter;
	}

	public static String leerString() {
		String texto = "";
		boolean valido = false;

		while (!valido) {
			texto = sc.nextLine().trim();

			if (texto != null && !texto.trim().isEmpty() && texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
				valido = true;
				return texto;
			} else {
				System.out.println("Error: introduce un texto valido");
			}
		}
		return texto;
	}

	public static String leerAlfanumericos() {
		String texto = "";
		boolean valido = false;

		while (!valido) {
			texto = sc.nextLine().trim();

			if (texto != null && !texto.trim().isEmpty() && texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+")) {
				valido = true;
				return texto;
			} else {
				System.out.println("Error: introduce un texto valido");
			}
		}
		return texto;
	}

	public static boolean leerConfirmacion() {
		while (true) {
			String input = sc.nextLine().trim().toUpperCase();

			if (input.isEmpty()) {
				System.out.println("Error: Debes introducir una respuesta.");
				continue;
			}

			char respuesta = input.charAt(0);

			if (respuesta == 'S')
				return true;
			if (respuesta == 'N')
				return false;

		}
	}

	public static LocalDate leerFecha() {
		LocalDate fecha = null;
		boolean valido = false;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		while (!valido) {
			try {
				String input = sc.nextLine().trim();

				if (input.isEmpty()) {
					System.out.println("Error: La fecha no puede estar vacía.");
					continue;
				}

				// Intenta parsear la fecha
				fecha = LocalDate.parse(input, formato);
				valido = true;

			} catch (DateTimeParseException e) {
				System.out.println("Error: Formato de fecha inválido. Usa el formato dd/MM/yyyy (ejemplo: 25/12/2023)");
			}
		}
		return fecha;
	}

	public static Date convertirLocalDateADate(LocalDate fecha) {
		if (fecha == null) {
			return null;
		}
		return Date.valueOf(fecha);
	}

	/**
	 * Convierte java.sql.Date a LocalDate
	 * 
	 * @param fecha java.sql.Date de la base de datos
	 * @return LocalDate
	 */
	public static LocalDate convertirDateALocalDate(Date fecha) {
		if (fecha == null) {
			return null;
		}
		return fecha.toLocalDate();
	}

	public static Date leerFechaSQL() {
		Date fecha = null;
		boolean valido = false;

		while (!valido) {
			try {
				String input = sc.nextLine().trim();

				if (input.isEmpty()) {
					System.out.println("Error: La fecha no puede estar vacía. Formato: YYYY-MM-DD");
					continue;
				}

				fecha = Date.valueOf(input);
				valido = true;

			} catch (IllegalArgumentException e) {
				System.out.println("Error: Formato inválido. Use YYYY-MM-DD (ejemplo: 2025-01-15)");
			}
		}
		return fecha;
	}

	public static LocalDate leerFechaSeparada() {
		LocalDate fecha = null;
		boolean valido = false;

		while (!valido) {
			try {
				System.out.print("Día (1-31): ");
				int dia = leerEnteroRango(1, 31);

				System.out.print("Mes (1-12): ");
				int mes = leerEnteroRango(1, 12);

				System.out.print("Año: ");
				int año = leerEntero();

				// Intentar crear la fecha para validar
				fecha = LocalDate.of(año, mes, dia);
				valido = true;

			} catch (java.time.DateTimeException e) {
				System.out.println("Error: La fecha introducida no es válida (ejemplo: 31/02 no existe).");
				System.out.println("Por favor, vuelve a introducir la fecha completa.\n");
			}
		}
		return fecha;
	}

	/**
	 * Lee una fecha dentro de un rango específico
	 * 
	 * @param fechaMin Fecha mínima permitida
	 * @param fechaMax Fecha máxima permitida
	 * @return LocalDate con la fecha introducida
	 */
	public static LocalDate leerFechaRango(LocalDate fechaMin, LocalDate fechaMax) {
		LocalDate fecha = null;
		boolean valido = false;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		while (!valido) {
			try {
				String input = sc.nextLine().trim();

				if (input.isEmpty()) {
					System.out.println("Error: La fecha no puede estar vacía.");
					continue;
				}

				fecha = LocalDate.parse(input, formato);

				// Validar rango
				if (fecha.isBefore(fechaMin) || fecha.isAfter(fechaMax)) {
					System.out.println("Error: La fecha debe estar entre " + fechaMin.format(formato) + " y "
							+ fechaMax.format(formato));
				} else {
					valido = true;
				}

			} catch (DateTimeParseException e) {
				System.out.println("Error: Formato de fecha inválido. Usa el formato dd/MM/yyyy");
			}
		}
		return fecha;
	}

	/**
	 * Lee una fecha y devuelve un array con [día, mes, año]
	 * 
	 * @return int[] con {día, mes, año}
	 */
	public static int[] leerFechaYSeparar() {
		LocalDate fecha = leerFecha();
		return new int[] { fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear() };
	}

	public static void cerrarScanner() {
		if (sc != null) {
			sc.close();
		}
	}
}