

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import modelos.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class MigrarMySQLaNeodatisLimpio {
    
    public static void main(String[] args) {
        
        System.out.println("🧹 Iniciando migración LIMPIA MySQL → Neodatis...\n");
        
        // ========== CONECTAR A MYSQL ==========
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        
        Session session = factory.openSession();
        
        // ========== BORRAR BD ANTIGUA Y CREAR NUEVA ==========
        String rutaNeodatis = "src/basedatos/ITtech.odb";
        
        
        
        ODB odb = ODBFactory.open(rutaNeodatis,"ivan","openpgpwd");
        
        try {
            
            // Mapas para guardar referencias
            Map<Integer, Cliente> clientesMap = new HashMap<>();
            Map<Integer, Curso> cursosMap = new HashMap<>();
            
            // ========== 1️⃣ MIGRAR CLIENTES (sin Sets) ==========
            System.out.println("👥 Migrando clientes...");
            
            List<Cliente> clientesMySQL = session.createQuery("from Cliente", Cliente.class).list();
            
            for (Cliente original : clientesMySQL) {
                Cliente limpio = new Cliente();
                limpio.setIdCliente(original.getIdCliente());
                limpio.setNombre(original.getNombre());
                limpio.setApellidos(original.getApellidos());
                limpio.setDireccion(original.getDireccion());
                limpio.setEdad(original.getEdad());
                // NO copiar inscripcioneses
                
                odb.store(limpio);
                clientesMap.put(limpio.getIdCliente(), limpio);
            }
            System.out.println("   ✅ " + clientesMySQL.size() + " clientes migrados");
            
            
            // ========== 2️⃣ MIGRAR CURSOS (sin Sets) ==========
            System.out.println("📚 Migrando cursos...");
            
            List<Curso> cursosMySQL = session.createQuery("from Curso", Curso.class).list();
            
            for (Curso original : cursosMySQL) {
                Curso limpio = new Curso();
                limpio.setIdCurso(original.getIdCurso());
                limpio.setNombreCurso(original.getNombreCurso());
                limpio.setDescripcion(original.getDescripcion());
                limpio.setDuracion(original.getDuracion());
                // NO copiar inscripcioneses
                
                odb.store(limpio);
                cursosMap.put(limpio.getIdCurso(), limpio);
            }
            System.out.println("   ✅ " + cursosMySQL.size() + " cursos migrados");
            
            
            // ========== 3️⃣ MIGRAR INSCRIPCIONES (con referencias) ==========
            System.out.println("📝 Migrando inscripciones...");
            
            List<Inscripcion> inscripcionesMySQL = session.createQuery("from Inscripcion", Inscripcion.class).list();
            
            for (Inscripcion original : inscripcionesMySQL) {
                Inscripcion limpia = new Inscripcion();
                limpia.setIdInscripcion(original.getIdInscripcion());
                limpia.setFechaInscripcion(original.getFechaInscripcion());
                
                // Establecer referencias a objetos YA guardados
                Cliente clienteRef = clientesMap.get(original.getClientes().getIdCliente());
                Curso cursoRef = cursosMap.get(original.getCursos().getIdCurso());
                
                limpia.setClientes(clienteRef);
                limpia.setCursos(cursoRef);
                
                odb.store(limpia);
            }
            System.out.println("   ✅ " + inscripcionesMySQL.size() + " inscripciones migradas");
            
            odb.commit();
            
            System.out.println("\n🎉 ¡Migración completada!");
            System.out.println("📂 Base de datos Neodatis: " + rutaNeodatis);
            System.out.println("📊 Resumen:");
            System.out.println("   - " + clientesMySQL.size() + " clientes");
            System.out.println("   - " + cursosMySQL.size() + " cursos");
            System.out.println("   - " + inscripcionesMySQL.size() + " inscripciones (con referencias completas)");
            System.out.println("👉 BD limpia, sin objetos null");
            
        } catch (Exception e) {
            e.printStackTrace();
            odb.rollback();
        } finally {
            odb.close();
            session.close();
            factory.close();
        }
    }
}