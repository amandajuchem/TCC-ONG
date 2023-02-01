import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimalAdocoesExcluirComponent } from './animal-adocoes-excluir.component';

describe('AnimalAdocoesExcluirComponent', () => {
  let component: AnimalAdocoesExcluirComponent;
  let fixture: ComponentFixture<AnimalAdocoesExcluirComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnimalAdocoesExcluirComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnimalAdocoesExcluirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
