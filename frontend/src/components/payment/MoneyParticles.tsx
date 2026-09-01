import { useEffect, useRef } from "react";

interface Particle {
  x: number;
  y: number;
  vx: number;
  vy: number;
  life: number;
  maxLife: number;
}

interface MoneyParticlesProps {
  isActive: boolean;
}

export default function MoneyParticles({ isActive }: MoneyParticlesProps) {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const particlesRef = useRef<Particle[]>([]);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas || !isActive) return;

    const ctx = canvas.getContext("2d");
    if (!ctx) return;

    // Set canvas size
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    // Create particles
    const createParticles = () => {
      const centerX = canvas.width / 2;
      const centerY = canvas.height / 2;

      for (let i = 0; i < 20; i++) {
        const angle = (Math.random() * Math.PI * 2);
        const speed = 2 + Math.random() * 4;

        particlesRef.current.push({
          x: centerX,
          y: centerY,
          vx: Math.cos(angle) * speed,
          vy: Math.sin(angle) * speed,
          life: 1,
          maxLife: 1,
        });
      }
    };

    // Animation loop
    let animationId: number;
    const animate = () => {
      ctx.clearRect(0, 0, canvas.width, canvas.height);

      particlesRef.current = particlesRef.current.filter((p) => p.life > 0);

      particlesRef.current.forEach((p) => {
        p.x += p.vx;
        p.y += p.vy;
        p.vy += 0.1; // gravity
        p.life -= 0.02;

        const opacity = p.life / p.maxLife;

        // Draw rupee symbol
        ctx.globalAlpha = opacity;
        ctx.fillStyle = "#F6E7C8";
        ctx.font = "bold 24px Arial";
        ctx.fillText("₹", p.x, p.y);
        ctx.globalAlpha = 1;
      });

      if (particlesRef.current.length > 0) {
        animationId = requestAnimationFrame(animate);
      }
    };

    // Start creating and animating particles
    const createInterval = setInterval(createParticles, 200);
    animate();

    return () => {
      clearInterval(createInterval);
      if (animationId) cancelAnimationFrame(animationId);
    };
  }, [isActive]);

  if (!isActive) return null;

  return (
    <canvas
      ref={canvasRef}
      className="pointer-events-none fixed inset-0 z-40"
    />
  );
}
