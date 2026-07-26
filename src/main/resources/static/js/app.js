document.addEventListener('DOMContentLoaded', function() {
  const autoTargets = document.querySelectorAll(
    '.food-card, .login-prompt-section, .emotion-grid, .result-effect-card, .result-image-wrap, .advice-card, .mypage-header'
  );
  autoTargets.forEach((el) => el.classList.add('scroll-animate'));

  const targets = Array.from(document.querySelectorAll('.scroll-animate'));
  if (targets.length === 0) return;

  if (!('IntersectionObserver' in window)) {
    targets.forEach((el) => el.classList.add('visible'));
    return;
  }

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (!entry.isIntersecting) return;
        entry.target.classList.add('visible');
        observer.unobserve(entry.target);
      });
    },
    { threshold: 0.12, rootMargin: '0px 0px -10% 0px' }
  );

  targets.forEach((el) => observer.observe(el));

  // Card Expansion on Click - Only on card-detail page
  if (window.location.pathname.includes('card-detail')) {
    document.addEventListener('click', function(e) {
      const card = e.target.closest('.pokemon-tcg-card');
      if (!card) return;
      
      // Don't expand if clicking on buttons or links inside the card
      if (e.target.closest('button, a, .btn')) return;
      
      // Toggle expanded state
      if (card.classList.contains('expanded')) {
        card.classList.remove('expanded');
        document.body.style.overflow = '';
      } else {
        card.classList.add('expanded');
        document.body.style.overflow = 'hidden';
      }
    });
  }

  // Close expanded card on Escape key
  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
      const expanded = document.querySelector('.pokemon-tcg-card.expanded');
      if (expanded) {
        expanded.classList.remove('expanded');
        document.body.style.overflow = '';
      }
    }
  });
});

